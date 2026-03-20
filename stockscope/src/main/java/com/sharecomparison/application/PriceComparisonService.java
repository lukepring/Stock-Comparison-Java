package com.sharecomparison.application;

import com.sharecomparison.domain.ChartData;
import com.sharecomparison.domain.ComparisonResult;
import com.sharecomparison.domain.PriceData;
import com.sharecomparison.infrastructure.ICacheStore;
import com.sharecomparison.infrastructure.IMarketDataClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PriceComparisonService implements IPriceComparisonService {

    private final ICacheStore cacheStore;
    private final IMarketDataClient marketDataClient;
    private final IChartBuilder chartBuilder;

    public PriceComparisonService(ICacheStore cacheStore, 
                                  IMarketDataClient marketDataClient, 
                                  IChartBuilder chartBuilder) {
        this.cacheStore = cacheStore;
        this.marketDataClient = marketDataClient;
        this.chartBuilder = chartBuilder;
    }

    // Enforce two-year validation rule
    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start.plusYears(2).isBefore(end)) {
            throw new IllegalArgumentException("Maximum range allowed is two years.");
        }
    }

    // Implement FetchSharePricesUseCase logic
    private List<PriceData> fetchSharePrices(String symbol, LocalDate start, LocalDate end) {
        validateDateRange(start, end); 
        List<PriceData> data = marketDataClient.fetch(symbol, start, end);
        cacheStore.save(symbol, data); // Requirement for persistence 
        return data;
    }

    // Implement CompareSharesUseCase logic
    @Override
    public ComparisonResult compare(String symbol1, String symbol2, 
                                    LocalDate startDate, LocalDate endDate) {
        
        // Execute Fetch Use Cases for both symbols
        List<PriceData> data1 = fetchSharePrices(symbol1, startDate, endDate);
        List<PriceData> data2 = fetchSharePrices(symbol2, startDate, endDate);

        ChartData chartData = chartBuilder.buildChart(data1, data2);
        
        return new ComparisonResult(symbol1, symbol2, startDate, endDate, data1, data2, chartData);
    }
}
