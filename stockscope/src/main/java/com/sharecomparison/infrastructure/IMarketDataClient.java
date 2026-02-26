package com.sharecomparison.infrastructure;

import com.sharecomparison.domain.PriceData;

import java.time.LocalDate;
import java.util.List;

public interface IMarketDataClient {

    List<PriceData> fetch(String symbol, LocalDate startDate, LocalDate endDate);
}
