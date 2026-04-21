package com.sharecomparison.application.pipeline;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sharecomparison.domain.PriceData;

@Component
@Order(2)
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
