package uk.ac.roehampton.stockscope.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import uk.ac.roehampton.stockscope.domain.model.DailyPrice;
import uk.ac.roehampton.stockscope.domain.model.ShareSymbol;
import uk.ac.roehampton.stockscope.domain.ports.PriceRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPriceRepository implements PriceRepository {

    private final Map<ShareSymbol, Map<LocalDate, DailyPrice>> store = new ConcurrentHashMap<>();

    @Override
    public void saveAll(List<DailyPrice> prices) {
        for (DailyPrice p : prices) {
            store.computeIfAbsent(p.symbol(), s -> new ConcurrentHashMap<>())
                    .put(p.date(), p);
        }
    }

    @Override
    public List<DailyPrice> findDailyPrices(ShareSymbol symbol, LocalDate from, LocalDate to) {
        Map<LocalDate, DailyPrice> byDate = store.getOrDefault(symbol, Map.of());
        List<DailyPrice> out = new ArrayList<>();
        LocalDate d = from;
        while (!d.isAfter(to)) {
            DailyPrice p = byDate.get(d);
            if (p != null) out.add(p);
            d = d.plusDays(1);
        }
        out.sort(Comparator.comparing(DailyPrice::date));
        return out;
    }

    @Override
    public boolean hasRange(ShareSymbol symbol, LocalDate from, LocalDate to) {
        Map<LocalDate, DailyPrice> byDate = store.get(symbol);
        if (byDate == null) return false;

        LocalDate d = from;
        while (!d.isAfter(to)) {
            if (!byDate.containsKey(d)) return false;
            d = d.plusDays(1);
        }
        return true;
    }
}