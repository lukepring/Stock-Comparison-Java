package com.sharecomparison.infrastructure;

public interface ICacheStore {

    void save(String key, Object data);

    Object load(String key);
}