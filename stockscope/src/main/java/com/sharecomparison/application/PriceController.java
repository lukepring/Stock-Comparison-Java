package com.sharecomparison.application;

import com.sharecomparison.domain.ComparisonResult;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PriceController implements IPriceController {

    private final IPriceComparisonService comparisonService;

    public PriceController(IPriceComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @Override
    public ComparisonResult comparePrices(String symbol1, String symbol2,
                                          LocalDate startDate, LocalDate endDate) {

        return comparisonService.compare(symbol1, symbol2, startDate, endDate);
    }
}
