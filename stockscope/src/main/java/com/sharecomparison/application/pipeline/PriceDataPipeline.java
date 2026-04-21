package com.sharecomparison.application.pipeline;

import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceDataPipeline {

    private final List<IPriceDataFilter> filters;

    public PriceDataPipeline(List<IPriceDataFilter> filters) {
        this.filters = filters;
    }

    public List<PriceData> process(List<PriceData> data) {
        if (data == null || data.isEmpty()) {
            return data;
        }

        List<PriceData> currentData = data;
        for (IPriceDataFilter filter : filters) {
            currentData = filter.filter(currentData);
        }
        return currentData;
    }
}
