package com.sharecomparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sharecomparison.application.MarketDataService;
import com.sharecomparison.application.PriceRepository;
import com.sharecomparison.infrastructure.InMemoryPriceRepository;
import com.sharecomparison.infrastructure.YahooFinanceService;

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
    public MarketDataService marketDataService(PriceRepository priceRepository) {
        return new YahooFinanceService(priceRepository);
    }
}