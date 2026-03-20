package com.sharecomparison.infrastructure;

import com.sharecomparison.domain.PriceData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocalCacheStore implements ICacheStore {

    private final Map<String, List<PriceData>> cache = new HashMap<>();

    @Override
    public void save(String key, List<PriceData> data) {
        cache.put(key, data);
    }

    @Override
    public List<PriceData> load(String key) {
        return cache.get(key);
    }
}
