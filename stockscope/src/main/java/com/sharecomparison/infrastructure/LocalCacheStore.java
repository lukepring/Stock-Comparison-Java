package com.sharecomparison.infrastructure;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocalCacheStore implements ICacheStore {

    private final Map<String, Object> cache = new HashMap<>();

    @Override
    public void save(String key, Object data) {
        cache.put(key, data);
    }

    @Override
    public Object load(String key) {
        return cache.get(key);
    }
}
