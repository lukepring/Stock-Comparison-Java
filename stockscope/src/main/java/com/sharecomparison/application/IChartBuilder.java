package com.sharecomparison.application;

import com.sharecomparison.domain.ChartData;
import com.sharecomparison.domain.PriceData;

import java.util.List;

public interface IChartBuilder {

    ChartData buildChart(List<PriceData> symbol1Data, List<PriceData> symbol2Data);
}
