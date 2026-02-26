package com.sharecomparison.application;

import com.sharecomparison.domain.ChartData;
import com.sharecomparison.domain.ChartPoint;
import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChartBuilder implements IChartBuilder {

    private static final double LEFT = 60.0;
    private static final double RIGHT = 860.0;
    private static final double TOP = 20.0;
    private static final double BOTTOM = 320.0;

    @Override
    public ChartData buildChart(List<PriceData> symbol1Data, List<PriceData> symbol2Data) {
        List<Double> prices1 = symbol1Data.stream().map(PriceData::getClosingPrice).toList();
        List<Double> prices2 = symbol2Data.stream().map(PriceData::getClosingPrice).toList();

        List<Double> allPrices = new ArrayList<>(prices1);
        allPrices.addAll(prices2);

        if (allPrices.isEmpty()) {
            return new ChartData(LEFT, RIGHT, TOP, BOTTOM, (TOP + BOTTOM) / 2,
                    "", "", List.of(), List.of());
        }

        double yMin = allPrices.stream().mapToDouble(Double::doubleValue).min().orElse(0) - 1.0;
        double yMax = allPrices.stream().mapToDouble(Double::doubleValue).max().orElse(1) + 1.0;
        int pointCount = Math.max(prices1.size(), prices2.size());
        double xMax = Math.max(1.0, pointCount - 1.0);

        List<ChartPoint> symbol1Points = buildPoints(prices1, xMax, yMin, yMax);
        List<ChartPoint> symbol2Points = buildPoints(prices2, xMax, yMin, yMax);

        return new ChartData(
                LEFT,
                RIGHT,
                TOP,
                BOTTOM,
                (TOP + BOTTOM) / 2,
                buildPath(symbol1Points),
                buildPath(symbol2Points),
                symbol1Points,
                symbol2Points
        );
    }

    private List<ChartPoint> buildPoints(List<Double> series, double xMax, double yMin, double yMax) {
        List<ChartPoint> points = new ArrayList<>();
        for (int idx = 0; idx < series.size(); idx++) {
            double value = series.get(idx);
            double x = LEFT + (idx / xMax) * (RIGHT - LEFT);
            double y = BOTTOM - ((value - yMin) / Math.max(1.0, yMax - yMin)) * (BOTTOM - TOP);
            points.add(new ChartPoint(round2(x), round2(y), round2(value)));
        }
        return points;
    }

    private String buildPath(List<ChartPoint> points) {
        if (points.isEmpty()) {
            return "";
        }

        StringBuilder path = new StringBuilder();
        for (int i = 0; i < points.size(); i++) {
            ChartPoint point = points.get(i);
            if (i == 0) {
                path.append("M");
            } else {
                path.append(" L");
            }
            path.append(point.getX()).append(",").append(point.getY());
        }
        return path.toString();
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
