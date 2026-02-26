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

    @Override
    public ComparisonResult compare(String symbol1, String symbol2,
                                    LocalDate startDate, LocalDate endDate) {

        List<PriceData> data1 = marketDataClient.fetch(symbol1, startDate, endDate);
        List<PriceData> data2 = marketDataClient.fetch(symbol2, startDate, endDate);

        cacheStore.save(symbol1, data1);
        cacheStore.save(symbol2, data2);

        ChartData chartData = chartBuilder.buildChart(data1, data2);
        return new ComparisonResult(symbol1, symbol2, startDate, endDate, data1, data2, chartData);
    }
}
