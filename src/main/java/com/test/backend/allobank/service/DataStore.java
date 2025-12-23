package com.test.backend.allobank.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataStore {
    private final Map<String, List<Map<String, Object>>> dataStore = new ConcurrentHashMap<>();

    public void loadData(String key, List<Map<String, Object>> data) {
        dataStore.put(key, List.copyOf(data));
    }

    public List<Map<String, Object>> getData(String key) {
        return dataStore.get(key);
    }
}
