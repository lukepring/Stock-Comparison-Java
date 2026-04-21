package com.sharecomparison.application.pipeline;

import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Acts as the 'Pipe' in the Pipes and Filters architecture.
 * Takes a collection of filters and applies them sequentially.
 */
@Component
public class PriceDataPipeline implements IPriceDataPipeline {

    private final List<IPriceDataFilter> filters;

    public PriceDataPipeline(List<IPriceDataFilter> filters) {
        this.filters = filters;
    }

    /**
     * Streams the dataset through every registered filter sequentially.
     */
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
