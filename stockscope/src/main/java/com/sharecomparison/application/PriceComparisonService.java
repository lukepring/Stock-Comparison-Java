package com.sharecomparison.application;

import com.sharecomparison.domain.ComparisonResult;
import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PriceComparisonService implements IPriceComparisonService {

    private final IFetchPrices fetchSharePricesUseCase;
    private final ICompareShares compareSharesUseCase;

    public PriceComparisonService(IFetchPrices fetchSharePricesUseCase, 
                                  ICompareShares compareSharesUseCase) {
        this.fetchSharePricesUseCase = fetchSharePricesUseCase;
        this.compareSharesUseCase = compareSharesUseCase;
    }

    @Override
    public ComparisonResult compare(String symbol1, String symbol2,
                                    LocalDate startDate, LocalDate endDate) {

        validateDateRange(startDate, endDate);

        List<PriceData> data1 = fetchSharePricesUseCase.fetch(symbol1, startDate, endDate);
        List<PriceData> data2 = fetchSharePricesUseCase.fetch(symbol2, startDate, endDate);

        return compareSharesUseCase.compare(symbol1, symbol2, startDate, endDate, data1, data2);
    }

    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and End dates must be provided.");
        }
        
        if (start.plusYears(2).isBefore(end)) {
            throw new IllegalArgumentException("The maximum date range permitted is two years.");
        }
        
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
    }
}