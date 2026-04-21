package com.sharecomparison.application.pipeline;

import java.util.List;

import com.sharecomparison.domain.PriceData;

public interface IPriceDataFilter {
    List<PriceData> filter(List<PriceData> input);
}
