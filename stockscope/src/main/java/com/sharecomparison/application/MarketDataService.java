package com.sharecomparison.application;

import java.time.LocalDate;
import java.util.List;

import com.sharecomparison.domain.PriceData;

public interface MarketDataService {

    List<PriceData> fetchSharePrices(
            String symbol,
            LocalDate startDate,
            LocalDate endDate
    );

}