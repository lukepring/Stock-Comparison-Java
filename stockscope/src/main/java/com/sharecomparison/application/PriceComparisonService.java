package com.sharecomparison.application;

import com.sharecomparison.domain.ChartData;
import com.sharecomparison.domain.ComparisonResult;
import com.sharecomparison.domain.PriceData;
import com.sharecomparison.infrastructure.ICacheStore;
import com.sharecomparison.infrastructure.IMarketDataClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

  
    @Override
    public ComparisonResult compare(String symbol1, String symbol2,
                                    LocalDate startDate, LocalDate endDate) {

   
        validateDateRange(startDate, endDate);

       
        List<PriceData> data1 = loadOrFetch(symbol1, startDate, endDate);
        List<PriceData> data2 = loadOrFetch(symbol2, startDate, endDate);

    
        ChartData chartData = chartBuilder.buildChart(data1, data2);
        
        return new ComparisonResult(symbol1, symbol2, startDate, endDate, data1, data2, chartData);
    }

   
    private List<PriceData> loadOrFetch(String symbol, LocalDate startDate, LocalDate endDate) {
     
        String cacheKey = symbol + ":" + startDate + ":" + endDate;


        List<PriceData> cached = cacheStore.load(cacheKey);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<PriceData> fetched = marketDataClient.fetch(symbol, startDate, endDate);
        
       
        if (fetched != null && !fetched.isEmpty()) {
            cacheStore.save(cacheKey, fetched);
        }
        
        return fetched;
    }


    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and End dates must be provided.");
        }
        
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        // Approximately 2 years (730 days)
        if (daysBetween > 730) {
            throw new IllegalArgumentException("The maximum date range permitted is two years.");
        }
        
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }
}