package uk.ac.roehampton.stockscope.domain.ports;

import uk.ac.roehampton.stockscope.domain.model.DailyPrice;
import uk.ac.roehampton.stockscope.domain.model.ShareSymbol;

import java.time.LocalDate;
import java.util.List;

public interface PriceDataSource {
    List<DailyPrice> fetchDailyPrices(ShareSymbol symbol, LocalDate from, LocalDate to);
}