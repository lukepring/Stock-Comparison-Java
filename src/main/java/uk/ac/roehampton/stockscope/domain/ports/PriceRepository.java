package uk.ac.roehampton.stockscope.domain.ports;

import uk.ac.roehampton.stockscope.domain.model.DailyPrice;
import uk.ac.roehampton.stockscope.domain.model.ShareSymbol;

import java.time.LocalDate;
import java.util.List;

public interface PriceRepository {
    void saveAll(List<DailyPrice> prices);
    List<DailyPrice> findDailyPrices(ShareSymbol symbol, LocalDate from, LocalDate to);
    boolean hasRange(ShareSymbol symbol, LocalDate from, LocalDate to);
}