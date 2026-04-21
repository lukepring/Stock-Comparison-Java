package com.sharecomparison.application.pipeline;

import com.sharecomparison.domain.PriceData;
import java.util.List;

/**
 * Interface representing a pipe/filter condition in the Pipes and Filters architecture.
 */
public interface IPriceDataFilter {
    
    /**
     * Filters or transforms the given list of price data points.
     *
     * @param input the input data
     * @return the filtered data
     */
    List<PriceData> filter(List<PriceData> input);
}
