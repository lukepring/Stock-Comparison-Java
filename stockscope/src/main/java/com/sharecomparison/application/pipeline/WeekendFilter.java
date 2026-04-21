package com.sharecomparison.application.pipeline;

import com.sharecomparison.domain.PriceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A concrete filter that removes any data points that occur on a weekend,
 * ensuring clean trading data without inactive day pollution.
 */
@Component
@Order(1) // Run this first
public class WeekendFilter implements IPriceDataFilter {

    private static final Logger log = LoggerFactory.getLogger(WeekendFilter.class);

    @Override
    public List<PriceData> filter(List<PriceData> input) {
        log.debug("Applying WeekendFilter to {} data points", input.size());
        return input.stream()
                .filter(data -> {
                    DayOfWeek day = data.getDate().getDayOfWeek();
                    return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
                })
                .collect(Collectors.toList());
    }
}
