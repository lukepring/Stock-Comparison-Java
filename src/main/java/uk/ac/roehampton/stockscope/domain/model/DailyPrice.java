package uk.ac.roehampton.stockscope.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyPrice(
        ShareSymbol symbol,
        LocalDate date,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        long volume
) {}