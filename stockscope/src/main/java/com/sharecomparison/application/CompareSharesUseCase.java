package com.sharecomparison.application;

import com.sharecomparison.domain.ChartData;
import com.sharecomparison.domain.ComparisonResult;
import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CompareSharesUseCase implements ICompareShares {

    private final IChartBuilder chartBuilder;

    public CompareSharesUseCase(IChartBuilder chartBuilder) {
        this.chartBuilder = chartBuilder;
    }

    public ComparisonResult compare(String symbol1, String symbol2,
                                    LocalDate startDate, LocalDate endDate,
                                    List<PriceData> data1, List<PriceData> data2) {
        
        ChartData chartData = chartBuilder.buildChart(data1, data2);
        
        return new ComparisonResult(symbol1, symbol2, startDate, endDate, data1, data2, chartData);
    }
}
