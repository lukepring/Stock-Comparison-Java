package com.sharecomparison.domain;

import java.time.LocalDate;

public class PriceData {

    private final String symbol;
    private final LocalDate date;
    private final double closingPrice;

    public PriceData(String symbol, LocalDate date, double closingPrice) {
        this.symbol = symbol;
        this.date = date;
        this.closingPrice = closingPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getClosingPrice() {
        return closingPrice;
    }
}