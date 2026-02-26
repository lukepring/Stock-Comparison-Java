package com.sharecomparison.domain;

import java.util.List;

public class ChartData {

    private final double left;
    private final double right;
    private final double top;
    private final double bottom;
    private final double midY;
    private final String symbol1Path;
    private final String symbol2Path;
    private final List<ChartPoint> symbol1Points;
    private final List<ChartPoint> symbol2Points;

    public ChartData(double left,
                     double right,
                     double top,
                     double bottom,
                     double midY,
                     String symbol1Path,
                     String symbol2Path,
                     List<ChartPoint> symbol1Points,
                     List<ChartPoint> symbol2Points) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.midY = midY;
        this.symbol1Path = symbol1Path;
        this.symbol2Path = symbol2Path;
        this.symbol1Points = symbol1Points;
        this.symbol2Points = symbol2Points;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public double getMidY() {
        return midY;
    }

    public String getSymbol1Path() {
        return symbol1Path;
    }

    public String getSymbol2Path() {
        return symbol2Path;
    }

    public List<ChartPoint> getSymbol1Points() {
        return symbol1Points;
    }

    public List<ChartPoint> getSymbol2Points() {
        return symbol2Points;
    }
}
