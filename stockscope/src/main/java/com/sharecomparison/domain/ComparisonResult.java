package com.sharecomparison.domain;

import java.time.LocalDate;
import java.util.List;

public class ComparisonResult {

    private final String symbol1;
    private final String symbol2;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<PriceData> symbol1Data;
    private final List<PriceData> symbol2Data;
    private final ChartData chartData;

    public ComparisonResult(String symbol1,
                            String symbol2,
                            LocalDate startDate,
                            LocalDate endDate,
                            List<PriceData> symbol1Data,
                            List<PriceData> symbol2Data,
                            ChartData chartData) {
        this.symbol1 = symbol1;
        this.symbol2 = symbol2;
        this.startDate = startDate;
        this.endDate = endDate;
        this.symbol1Data = symbol1Data;
        this.symbol2Data = symbol2Data;
        this.chartData = chartData;
    }

    public String getSymbol1() {
        return symbol1;
    }

    public String getSymbol2() {
        return symbol2;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<PriceData> getSymbol1Data() {
        return symbol1Data;
    }

    public List<PriceData> getSymbol2Data() {
        return symbol2Data;
    }

    public ChartData getChartData() {
        return chartData;
    }
}
