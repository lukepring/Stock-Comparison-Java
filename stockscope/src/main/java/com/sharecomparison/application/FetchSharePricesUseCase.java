package com.sharecomparison.application;

import com.sharecomparison.application.pipeline.IPriceDataPipeline;
import com.sharecomparison.domain.PriceData;
import com.sharecomparison.infrastructure.ICacheStore;
import com.sharecomparison.infrastructure.IMarketDataClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FetchSharePricesUseCase implements IFetchPrices {

    private final ICacheStore cacheStore;
    private final IMarketDataClient marketDataClient;
    private final IPriceDataPipeline priceDataPipeline;

    public FetchSharePricesUseCase(ICacheStore cacheStore, 
                                   IMarketDataClient marketDataClient,
                                   IPriceDataPipeline priceDataPipeline) {
        this.cacheStore = cacheStore;
        this.marketDataClient = marketDataClient;
        this.priceDataPipeline = priceDataPipeline;
    }

    public List<PriceData> fetch(String symbol, LocalDate startDate, LocalDate endDate) {
        String cacheKey = symbol + ":" + startDate + ":" + endDate;

        List<PriceData> cached = cacheStore.load(cacheKey);
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }

        List<PriceData> fetched = marketDataClient.fetch(symbol, startDate, endDate);
        
        if (fetched != null && !fetched.isEmpty()) {
            // Apply Pipes and Filters architecture style via the PriceDataPipeline
            fetched = priceDataPipeline.process(fetched);
            cacheStore.save(cacheKey, fetched);
        }
        
        return fetched;
    }
}
