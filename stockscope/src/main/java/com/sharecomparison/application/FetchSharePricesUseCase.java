package com.sharecomparison.application;

import com.sharecomparison.domain.PriceData;
import com.sharecomparison.infrastructure.ICacheStore;
import com.sharecomparison.infrastructure.IMarketDataClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FetchSharePricesUseCase {

    private final ICacheStore cacheStore;
    private final IMarketDataClient marketDataClient;

    public FetchSharePricesUseCase(ICacheStore cacheStore, IMarketDataClient marketDataClient) {
        this.cacheStore = cacheStore;
        this.marketDataClient = marketDataClient;
    }

    public List<PriceData> fetch(String symbol, LocalDate startDate, LocalDate endDate) {
        String cacheKey = symbol + ":" + startDate + ":" + endDate;

        List<PriceData> cached = cacheStore.load(cacheKey);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<PriceData> fetched = marketDataClient.fetch(symbol, startDate, endDate);
        
        if (fetched != null && !fetched.isEmpty()) {
            cacheStore.save(cacheKey, fetched);
        }
        
        return fetched;
    }
}
