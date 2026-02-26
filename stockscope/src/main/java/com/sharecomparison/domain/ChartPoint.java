package com.sharecomparison.domain;

public class ChartPoint {

    private final double x;
    private final double y;
    private final double value;

    public ChartPoint(double x, double y, double value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getValue() {
        return value;
    }
}
