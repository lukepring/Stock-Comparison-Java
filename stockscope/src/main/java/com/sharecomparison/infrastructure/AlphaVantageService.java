package com.sharecomparison.infrastructure;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharecomparison.application.MarketDataService;
import com.sharecomparison.application.PriceRepository;
import com.sharecomparison.domain.PriceData;

public class AlphaVantageService implements MarketDataService {

    private static final Logger log = LoggerFactory.getLogger(AlphaVantageService.class);
    private static final int LOG_SNIPPET_LENGTH = 250;
    private static final long MIN_REQUEST_INTERVAL_MS = 1100;
    private static final Object RATE_LIMIT_LOCK = new Object();
    private static long lastRequestAtMs = 0L;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final PriceRepository repository;

    private final String apiKey;
    private final String outputSize;

    public AlphaVantageService(PriceRepository repository) {
        this(repository, null);
    }

    public AlphaVantageService(PriceRepository repository, String apiKey) {
        this(repository, apiKey, null);
    }

    public AlphaVantageService(PriceRepository repository, String apiKey, String outputSize) {
        this.repository = repository;
        this.apiKey = resolveApiKey(apiKey);
        this.outputSize = normalizeOutputSize(outputSize);
    }

    @Override
    public List<PriceData> fetchSharePrices(String symbol, LocalDate startDate, LocalDate endDate) {
        try {
            if (symbol == null || symbol.isBlank()) {
                return repository.loadPrices(symbol, startDate, endDate);
            }

            List<PriceData> cached = repository.loadPrices(symbol, startDate, endDate);
            if (!cached.isEmpty()) {
                log.debug("Returning cached prices for symbol={} ({} to {})", symbol, startDate, endDate);
                return cached;
            }

            String json = fetchTimeSeriesDailyJson(symbol, outputSize);
            if (json == null) {
                return repository.loadPrices(symbol, startDate, endDate);
            }

            if (json.contains("\"Note\"")) {
                log.warn("Alpha Vantage throttled request: {}", extractStringFieldOrSnippet(json, "Note"));
                return repository.loadPrices(symbol, startDate, endDate);
            }
            if (json.contains("\"Information\"")) {
                log.warn("Alpha Vantage returned information: {}", extractStringFieldOrSnippet(json, "Information"));
                if ("full".equals(outputSize)
                        && json.contains("outputsize=full")
                        && (json.toLowerCase().contains("premium") || json.toLowerCase().contains("subscribe"))) {
                    log.warn("Retrying Alpha Vantage request with outputsize=compact (free-tier fallback).");
                    String fallbackJson = fetchTimeSeriesDailyJson(symbol, "compact");
                    if (fallbackJson != null
                            && !fallbackJson.contains("\"Information\"")
                            && !fallbackJson.contains("\"Note\"")
                            && !fallbackJson.contains("\"Error Message\"")
                            && !fallbackJson.contains("\"error message\"")) {
                        json = fallbackJson;
                    } else {
                        return repository.loadPrices(symbol, startDate, endDate);
                    }
                } else {
                    return repository.loadPrices(symbol, startDate, endDate);
                }
            }
            if (json.contains("\"Error Message\"") || json.contains("\"error message\"")) {
                log.warn("Alpha Vantage error for symbol={}: {}", symbol, extractStringFieldOrSnippet(json, "Error Message"));
                return repository.loadPrices(symbol, startDate, endDate);
            }

            List<PriceData> prices = parseTimeSeriesDailyJson(symbol, json, startDate, endDate);

            if (prices.isEmpty()) {
                log.warn("Alpha Vantage returned no price data for symbol={}. Response snippet: {}", symbol, snippet(json));
                return repository.loadPrices(symbol, startDate, endDate);
            }

            repository.savePrices(prices);
            return prices;

        } catch (Exception e) {
            log.warn("Alpha Vantage fetch failed for symbol={}: {}", symbol, e.toString());
            return repository.loadPrices(symbol, startDate, endDate);
        }
    }

    private String fetchTimeSeriesDailyJson(String symbol, String outputSize) throws Exception {
        rateLimit();
        String url = "https://www.alphavantage.co/query" +
                "?function=TIME_SERIES_DAILY" +
                "&symbol=" + URLEncoder.encode(symbol.trim(), StandardCharsets.UTF_8) +
                "&outputsize=" + URLEncoder.encode(normalizeOutputSize(outputSize), StandardCharsets.UTF_8) +
                "&apikey=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            log.warn("Alpha Vantage HTTP error: status={}", response.statusCode());
            return null;
        }
        return response.body();
    }

    private static void rateLimit() {
        synchronized (RATE_LIMIT_LOCK) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRequestAtMs;
            if (elapsed < MIN_REQUEST_INTERVAL_MS) {
                long sleepMs = MIN_REQUEST_INTERVAL_MS - elapsed;
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
            lastRequestAtMs = System.currentTimeMillis();
        }
    }

    List<PriceData> parseTimeSeriesDailyJson(
            String symbol,
            String json,
            LocalDate start,
            LocalDate end) {

        List<PriceData> prices = new ArrayList<>();

        int timeSeriesIndex = json.indexOf("\"Time Series (Daily)\"");
        if (timeSeriesIndex == -1) {
            log.warn("No 'Time Series (Daily)' section found in Alpha Vantage response");
            return prices;
        }

        int startObj = json.indexOf('{', timeSeriesIndex);
        if (startObj == -1) return prices;

        int braceCount = 1;
        int cursor = startObj + 1;
        while (cursor < json.length() && braceCount > 0) {
            char c = json.charAt(cursor);
            if (c == '{') braceCount++;
            if (c == '}') braceCount--;
            cursor++;
        }
        if (braceCount != 0) {
            log.warn("Unbalanced braces while parsing 'Time Series (Daily)'");
            return prices;
        }

        String timeSeriesBlock = json.substring(startObj, cursor);

        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

        Pattern entryPattern = Pattern.compile(
                "\"(\\d{4}-\\d{2}-\\d{2})\"\\s*:\\s*\\{[^}]*?\"4\\. close\"\\s*:\\s*\"([^\"]+)\"",
                Pattern.DOTALL
        );

        Matcher matcher = entryPattern.matcher(timeSeriesBlock);
        while (matcher.find()) {
            String dateStr = matcher.group(1);
            String closeStr = matcher.group(2);
            LocalDate date;
            try {
                date = LocalDate.parse(dateStr, fmt);
            } catch (Exception ex) {
                continue;
            }

            if (date.isBefore(start) || date.isAfter(end)) continue;

            double closePrice;
            try {
                closePrice = Double.parseDouble(closeStr);
            } catch (NumberFormatException ex) {
                continue;
            }

            prices.add(new PriceData(symbol, date, closePrice));
        }

        // Sort just in case
        prices.sort((a, b) -> a.getDate().compareTo(b.getDate()));

        return prices;
    }

    private static String resolveApiKey(String apiKey) {
        String resolvedKey = (apiKey == null || apiKey.isBlank())
                ? System.getenv("ALPHAVANTAGE_KEY")
                : apiKey;
        if (resolvedKey == null || resolvedKey.isBlank()) {
            log.warn("Alpha Vantage API key not set (property/env). Falling back to 'demo' key.");
            resolvedKey = "demo";
        }
        if ("demo".equalsIgnoreCase(resolvedKey)) {
            log.warn("Alpha Vantage is configured with the 'demo' key (very limited symbols). Set ALPHAVANTAGE_KEY (or alphavantage.key) to use a real key.");
        }
        return resolvedKey;
    }

    private static String normalizeOutputSize(String outputSize) {
        if (outputSize == null || outputSize.isBlank()) return "compact";
        String normalized = outputSize.trim().toLowerCase();
        if (!"compact".equals(normalized) && !"full".equals(normalized)) {
            log.warn("Invalid alphavantage.outputsize='{}'. Falling back to 'compact'.", outputSize);
            return "compact";
        }
        return normalized;
    }

    private static String extractStringFieldOrSnippet(String json, String fieldName) {
        String extracted = extractStringField(json, fieldName);
        return extracted != null ? extracted : snippet(json);
    }

    private static String extractStringField(String json, String fieldName) {
        if (json == null || json.isBlank() || fieldName == null || fieldName.isBlank()) return null;
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(fieldName) + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) return null;
        return matcher.group(1);
    }

    private static String snippet(String value) {
        if (value == null) return "null";
        String trimmed = value.trim();
        if (trimmed.length() <= LOG_SNIPPET_LENGTH) return trimmed;
        return trimmed.substring(0, LOG_SNIPPET_LENGTH) + "...";
    }
}
