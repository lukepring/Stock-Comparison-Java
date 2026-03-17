package com.sharecomparison.infrastructure;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.sharecomparison.application.MarketDataService;
import com.sharecomparison.application.PriceRepository;
import com.sharecomparison.domain.PriceData;

public class YahooFinanceService implements MarketDataService {

    private PriceRepository repository;

    public YahooFinanceService(PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PriceData> fetchSharePrices(
            String symbol,
            LocalDate startDate,
            LocalDate endDate) {

        try {

            long startEpoch = startDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
            long endEpoch = endDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);

            String urlString =
                    "https://query1.finance.yahoo.com/v7/finance/download/"
                            + symbol
                            + "?period1=" + startEpoch
                            + "&period2=" + endEpoch
                            + "&interval=1d&events=history";

            URL url = new URL(urlString);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    );

            List<PriceData> prices =
                    parseCSV(symbol, reader);

            repository.savePrices(prices);

            return prices;

        } catch (Exception e) {

            System.out.println("API failed. Using cached data.");

            return repository.loadPrices(symbol, startDate, endDate);
        }
    }

    private List<PriceData> parseCSV(
            String symbol,
            BufferedReader reader) throws Exception {

        List<PriceData> prices = new ArrayList<>();

        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {

            String[] values = line.split(",");

            if (values.length < 5) continue;

            LocalDate date =
                    LocalDate.parse(values[0]);

            double closePrice =
                    Double.parseDouble(values[4]);

            PriceData data =
                    new PriceData(symbol, date, closePrice);

            prices.add(data);
        }

        return prices;
    }
}