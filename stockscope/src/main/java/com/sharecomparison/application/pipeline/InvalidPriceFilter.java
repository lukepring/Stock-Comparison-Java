package com.sharecomparison.application.pipeline;

import com.sharecomparison.domain.PriceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A concrete filter that removes any invalid data points such as negative or zero closing prices.
 */
@Component
@Order(2) // Run after weekend filtration
public class InvalidPriceFilter implements IPriceDataFilter {

    private static final Logger log = LoggerFactory.getLogger(InvalidPriceFilter.class);

    @Override
    public List<PriceData> filter(List<PriceData> input) {
        log.debug("Applying InvalidPriceFilter to {} data points", input.size());
        return input.stream()
                .filter(data -> data.getClosingPrice() > 0.0)
                .collect(Collectors.toList());
    }
}
