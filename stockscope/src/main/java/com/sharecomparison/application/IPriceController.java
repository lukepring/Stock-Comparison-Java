package com.sharecomparison.application;

import com.sharecomparison.domain.ComparisonResult;

import java.time.LocalDate;

public interface IPriceController {

    ComparisonResult comparePrices(String symbol1, String symbol2,
                                   LocalDate startDate, LocalDate endDate);
}
