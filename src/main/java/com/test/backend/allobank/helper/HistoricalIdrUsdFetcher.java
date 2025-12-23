package com.test.backend.allobank.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("historicalIdrUsdFetcher")
public class HistoricalIdrUsdFetcher implements DataFetcher {

    private final WebClient webClient;

    public HistoricalIdrUsdFetcher(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Map<String, Object>> fetchData() {
        Map<String, Object> response = webClient.get()
                .uri("/2024-01-01..2024-01-05?from=IDR&to=USD")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Map<String, Double>> rates = (Map<String, Map<String, Double>>) response.get("rates");

        List<Map<String, Object>> result = new ArrayList<>();
        rates.forEach((date, rateData) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("date", date);
            data.put("rate", rateData.get("USD"));
            result.add(data);
        });

        return result;
    }
}
