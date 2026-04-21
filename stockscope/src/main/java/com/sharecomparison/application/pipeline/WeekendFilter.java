package com.sharecomparison.application.pipeline;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sharecomparison.domain.PriceData;

@Component
@Order(1)
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
