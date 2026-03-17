package com.sharecomparison.application;

import java.time.LocalDate;
import java.util.List;

import com.sharecomparison.domain.PriceData;

public interface PriceRepository {

    void savePrices(List<PriceData> prices);

    List<PriceData> loadPrices(
            String symbol,
            LocalDate startDate,
            LocalDate endDate
    );

}