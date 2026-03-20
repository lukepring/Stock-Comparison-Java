package com.sharecomparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

import com.sharecomparison.application.MarketDataService;
import com.sharecomparison.application.PriceRepository;
import com.sharecomparison.infrastructure.AlphaVantageService;
import com.sharecomparison.infrastructure.InMemoryPriceRepository;

@SpringBootApplication
public class StockComparisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockComparisonApplication.class, args);
    }

    @Bean
    public PriceRepository priceRepository() {
        return new InMemoryPriceRepository();
    }

    @Bean
    public MarketDataService marketDataService(
            PriceRepository repository,
            @Value("${alphavantage.key:demo}") String apiKey,
            @Value("${alphavantage.outputsize:compact}") String outputSize) {
        return new AlphaVantageService(repository, apiKey, outputSize);
    }
}
