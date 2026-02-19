package uk.ac.roehampton.stockscope.infrastructure.datasource;

import org.springframework.stereotype.Component;
import uk.ac.roehampton.stockscope.domain.model.DailyPrice;
import uk.ac.roehampton.stockscope.domain.model.ShareSymbol;
import uk.ac.roehampton.stockscope.domain.ports.PriceDataSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PriceDataSourceStub implements PriceDataSource {

    @Override
    public List<DailyPrice> fetchDailyPrices(ShareSymbol symbol, LocalDate from, LocalDate to) {
        List<DailyPrice> out = new ArrayList<>();
        LocalDate d = from;

        BigDecimal base = BigDecimal.valueOf(Math.abs(symbol.value().hashCode() % 200) + 50);

        while (!d.isAfter(to)) {
            BigDecimal close = base.add(BigDecimal.valueOf(d.getDayOfMonth() % 10));
            out.add(new DailyPrice(
                    symbol, d,
                    close.subtract(BigDecimal.ONE),
                    close.add(BigDecimal.ONE),
                    close.subtract(BigDecimal.valueOf(2)),
                    close,
                    1_000_000L
            ));
            d = d.plusDays(1);
        }
        return out;
    }
}