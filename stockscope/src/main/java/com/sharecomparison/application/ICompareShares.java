package com.sharecomparison.application;

import com.sharecomparison.domain.ComparisonResult;
import com.sharecomparison.domain.PriceData;

import java.time.LocalDate;
import java.util.List;

public interface ICompareShares {
    ComparisonResult compare(String symbol1, String symbol2,
                             LocalDate startDate, LocalDate endDate,
                             List<PriceData> data1, List<PriceData> data2);
}
