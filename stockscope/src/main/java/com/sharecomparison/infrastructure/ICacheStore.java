package com.sharecomparison.infrastructure;

import com.sharecomparison.domain.PriceData;
import java.util.List;

public interface ICacheStore {
    void save(String key, List<PriceData> data);
    List<PriceData> load(String key);
}