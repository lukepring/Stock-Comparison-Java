package com.sharecomparison.application;

import com.sharecomparison.domain.PriceData;

import java.time.LocalDate;
import java.util.List;

public interface IFetchPrices {
    List<PriceData> fetch(String symbol, LocalDate startDate, LocalDate endDate);
}
