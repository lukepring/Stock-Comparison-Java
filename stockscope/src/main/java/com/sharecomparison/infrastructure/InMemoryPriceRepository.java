package com.sharecomparison.infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sharecomparison.application.PriceRepository;
import com.sharecomparison.domain.PriceData;

public class InMemoryPriceRepository implements PriceRepository {

    private List<PriceData> storedPrices = new ArrayList<>();

    @Override
    public void savePrices(List<PriceData> prices) {
        for (PriceData newPrice : prices) {
            boolean replaced = false;

            for (int i = 0; i < storedPrices.size(); i++) {
                PriceData existing = storedPrices.get(i);

                if (existing.getSymbol().equals(newPrice.getSymbol())
                        && existing.getDate().equals(newPrice.getDate())) {
                    storedPrices.set(i, newPrice);
                    replaced = true;
                    break;
                }
            }

            if (!replaced) {
                storedPrices.add(newPrice);
            }
        }
    }

    @Override
    public List<PriceData> loadPrices(
            String symbol,
            LocalDate startDate,
            LocalDate endDate) {

        List<PriceData> results = new ArrayList<>();

        for (PriceData p : storedPrices) {

            if (p.getSymbol().equals(symbol)
                    && !p.getDate().isBefore(startDate)
                    && !p.getDate().isAfter(endDate)) {

                results.add(p);
            }
        }

        return results;
    }
}