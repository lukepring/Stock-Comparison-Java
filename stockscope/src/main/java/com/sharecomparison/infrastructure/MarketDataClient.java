package com.sharecomparison.infrastructure;

import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MarketDataClient implements IMarketDataClient {

    @Override
    public List<PriceData> fetch(String symbol, LocalDate startDate, LocalDate endDate) {

        Random random = new Random(symbol.hashCode());
        List<PriceData> data = new ArrayList<>();

        double price = 100 + random.nextDouble() * 50;
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            price += random.nextDouble() * 4 - 2;
            if (price < 1) {
                price = 1;
            }
            data.add(new PriceData(symbol, current, Math.round(price * 100.0) / 100.0));
            current = current.plusDays(1);
        }

        return data;
    }
}
