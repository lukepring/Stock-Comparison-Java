package com.sharecomparison.application;

import com.sharecomparison.domain.ChartData;
import com.sharecomparison.domain.ChartPoint;
import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        List<PriceData> allData = new ArrayList<>(symbol1Data);
        allData.addAll(symbol2Data);

        if (allData.isEmpty()) {
            return new ChartData(LEFT, RIGHT, TOP, BOTTOM, (TOP + BOTTOM) / 2,
                    0.0, 0.0, "", "", "", "", List.of(), List.of());
        }

        double trueMinPrice = allData.stream().mapToDouble(PriceData::getClosingPrice).min().orElse(0.0);
        double trueMaxPrice = allData.stream().mapToDouble(PriceData::getClosingPrice).max().orElse(0.0);

        double yMin = trueMinPrice - 1.0;
        double yMax = trueMaxPrice + 1.0;

        LocalDate minDate = allData.stream().map(PriceData::getDate).min(LocalDate::compareTo).orElse(LocalDate.now());
        LocalDate maxDate = allData.stream().map(PriceData::getDate).max(LocalDate::compareTo).orElse(LocalDate.now());
        long totalDays = ChronoUnit.DAYS.between(minDate, maxDate);

        List<ChartPoint> symbol1Points = buildPoints(symbol1Data, minDate, totalDays, yMin, yMax);
        List<ChartPoint> symbol2Points = buildPoints(symbol2Data, minDate, totalDays, yMin, yMax);

        return new ChartData(
                LEFT,
                RIGHT,
                TOP,
                BOTTOM,
                (TOP + BOTTOM) / 2,
                trueMinPrice,
                trueMaxPrice,
                minDate.toString(),
                maxDate.toString(),
                buildPath(symbol1Points),
                buildPath(symbol2Points),
                symbol1Points,
                symbol2Points
        );
    }

    private List<ChartPoint> buildPoints(List<PriceData> series, LocalDate minDate, long totalDays, double yMin, double yMax) {
        List<ChartPoint> points = new ArrayList<>();
        for (PriceData data : series) {
            double value = data.getClosingPrice();
            long daysFromStart = ChronoUnit.DAYS.between(minDate, data.getDate());
            double xPosNormalized = totalDays == 0 ? 0.5 : (double) daysFromStart / totalDays;
            double x = LEFT + xPosNormalized * (RIGHT - LEFT);
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
