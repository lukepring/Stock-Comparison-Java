package com.sharecomparison.presentation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sharecomparison.application.IPriceController;
import com.sharecomparison.domain.ComparisonResult;

@Controller
public class WebPageController {

    private final IPriceController priceController;

    public WebPageController(IPriceController priceController) {
        this.priceController = priceController;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        // Defaults for empty form on first visit
        model.addAttribute("symbol1", "AAPL");
        model.addAttribute("symbol2", "MSFT");
        model.addAttribute("startDate", LocalDate.now().minusYears(1).toString());
        model.addAttribute("endDate", LocalDate.now().toString());
        return "index";
    }

    @GetMapping("/compare")
    public String compare(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {

        LocalDate start = (startDate != null && !startDate.isBlank()) ? LocalDate.parse(startDate) : LocalDate.now().minusYears(1);
        LocalDate end = (endDate != null && !endDate.isBlank()) ? LocalDate.parse(endDate) : LocalDate.now();

        symbol1 = symbol1.trim().toUpperCase();
        symbol2 = symbol2.trim().toUpperCase();

        ComparisonResult result = priceController.comparePrices(symbol1, symbol2, start, end);

        if (result.getSymbol1Data().isEmpty() || result.getSymbol2Data().isEmpty()) {
            model.addAttribute("error", "No data available for one or both symbols in this range.");
            model.addAttribute("symbol1", symbol1);
            model.addAttribute("symbol2", symbol2);
            model.addAttribute("startDate", start.toString());
            model.addAttribute("endDate", end.toString());
            return "index";
        }

        data1.sort(Comparator.comparing(PriceData::getDate));
        data2.sort(Comparator.comparing(PriceData::getDate));

        // Chart coords calculation
        double chartWidth = 800, chartHeight = 300;
        double left = 80, right = left + chartWidth;
        double bottom = 320, top = bottom - chartHeight;
        double midY = top + chartHeight / 2;

        double minPrice = Double.MAX_VALUE, maxPrice = Double.MIN_VALUE;
        for (PriceData p : data1) {
            minPrice = Math.min(minPrice, p.getClosingPrice());
            maxPrice = Math.max(maxPrice, p.getClosingPrice());
        }
        for (PriceData p : data2) {
            minPrice = Math.min(minPrice, p.getClosingPrice());
            maxPrice = Math.max(maxPrice, p.getClosingPrice());
        }
        if (maxPrice == minPrice) maxPrice += 1.0;
        double priceRange = maxPrice - minPrice;

        // Align series by date: only plot dates present in both data sets
        Map<LocalDate, PriceData> map1 = data1.stream()
                .collect(Collectors.toMap(PriceData::getDate, p -> p, (existing, replacement) -> existing));
        Map<LocalDate, PriceData> map2 = data2.stream()
                .collect(Collectors.toMap(PriceData::getDate, p -> p, (existing, replacement) -> existing));

        Set<LocalDate> commonDates = new LinkedHashSet<>(map1.keySet());
        commonDates.retainAll(map2.keySet());
        List<LocalDate> sortedDates = new ArrayList<>(commonDates);
        sortedDates.sort(Comparator.naturalOrder());

        int n = sortedDates.size();
        if (n == 0) {
            model.addAttribute("error", "No overlapping trading dates found for the two symbols in this range.");
            model.addAttribute("symbol1", symbol1);
            model.addAttribute("symbol2", symbol2);
            model.addAttribute("startDate", start.toString());
            model.addAttribute("endDate", end.toString());
            return "index";
        }
        double xStep = n > 1 ? chartWidth / (n - 1) : 0;

        StringBuilder path1 = new StringBuilder();
        StringBuilder path2 = new StringBuilder();
        List<Map<String, Object>> points1 = new ArrayList<>();
        List<Map<String, Object>> points2 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double x = left + i * xStep;
            LocalDate date = sortedDates.get(i);

            PriceData p1 = map1.get(date);
            double y1 = bottom - ((p1.getClosingPrice() - minPrice) / priceRange) * chartHeight;
            path1.append(i == 0 ? "M" : "L").append(x).append(",").append(y1).append(" ");
            points1.add(Map.of("x", x, "y", y1, "value", p1.getClosingPrice(), "date", date.toString()));

            PriceData p2 = map2.get(date);
            double y2 = bottom - ((p2.getClosingPrice() - minPrice) / priceRange) * chartHeight;
            path2.append(i == 0 ? "M" : "L").append(x).append(",").append(y2).append(" ");
            points2.add(Map.of("x", x, "y", y2, "value", p2.getClosingPrice(), "date", date.toString()));
        }

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("left", left);
        chartData.put("right", right);
        chartData.put("top", top);
        chartData.put("bottom", bottom);
        chartData.put("midY", midY);
        chartData.put("symbol1Path", path1.toString());
        chartData.put("symbol2Path", path2.toString());
        chartData.put("symbol1Points", points1);
        chartData.put("symbol2Points", points2);

        Map<String, Object> result = new HashMap<>();
        result.put("symbol1", symbol1);
        result.put("symbol2", symbol2);
        result.put("startDate", start);
        result.put("endDate", end);
        result.put("chartData", chartData);
        result.put("symbol1Data", data1);
        result.put("symbol2Data", data2);

        model.addAttribute("result", result);
        model.addAttribute("symbol1", symbol1);
        model.addAttribute("symbol2", symbol2);
        model.addAttribute("startDate", start.toString());
        model.addAttribute("endDate", end.toString());

        return "index";
    }
}