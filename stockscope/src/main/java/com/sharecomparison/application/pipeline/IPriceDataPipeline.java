package com.sharecomparison.application.pipeline;

import com.sharecomparison.domain.PriceData;

import java.util.List;

public interface IPriceDataPipeline {
    List<PriceData> process(List<PriceData> data);
}
