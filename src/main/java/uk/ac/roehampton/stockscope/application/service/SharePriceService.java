package uk.ac.roehampton.stockscope.application.service;

import org.springframework.stereotype.Service;
import uk.ac.roehampton.stockscope.domain.model.DailyPrice;
import uk.ac.roehampton.stockscope.domain.model.ShareSymbol;
import uk.ac.roehampton.stockscope.domain.ports.PriceDataSource;
import uk.ac.roehampton.stockscope.domain.ports.PriceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SharePriceService {

    private final PriceDataSource dataSource;
    private final PriceRepository repository;

    public SharePriceService(PriceDataSource dataSource, PriceRepository repository) {
        this.dataSource = dataSource;
        this.repository = repository;
    }

    public List<DailyPrice> getDailyPrices(ShareSymbol symbol, LocalDate from, LocalDate to) {
        validateRange(from, to);

        if (repository.hasRange(symbol, from, to)) {
            return repository.findDailyPrices(symbol, from, to);
        }

        List<DailyPrice> fetched = dataSource.fetchDailyPrices(symbol, from, to);
        repository.saveAll(fetched);
        return fetched;
    }

    public List<DailyPrice> compareTwoSymbols(ShareSymbol a, ShareSymbol b, LocalDate from, LocalDate to) {
        List<DailyPrice> result = new ArrayList<>();
        result.addAll(getDailyPrices(a, from, to));
        result.addAll(getDailyPrices(b, from, to));
        return result;
    }

    private void validateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) throw new IllegalArgumentException("Dates cannot be null");
        if (from.isAfter(to)) throw new IllegalArgumentException("From must be before To");
        if (from.plusYears(2).isBefore(to)) {
            throw new IllegalArgumentException("Date range cannot exceed 2 years");
        }
    }
}