package com.test.backend.allobank.helper;

import java.util.List;
import java.util.Map;

public interface DataFetcher {
    List<Map<String, Object>> fetchData();
}
