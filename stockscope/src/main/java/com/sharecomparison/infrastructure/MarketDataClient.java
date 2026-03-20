package com.sharecomparison.infrastructure;

import com.sharecomparison.application.MarketDataService;
import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MarketDataClient implements IMarketDataClient {

    private final MarketDataService marketDataService;

    public MarketDataClient(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Override
    public List<PriceData> fetch(String symbol, LocalDate startDate, LocalDate endDate) {
        return marketDataService.fetchSharePrices(symbol, startDate, endDate);
    }
}
