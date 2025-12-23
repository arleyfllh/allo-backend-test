package com.test.backend.allobank.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("latestIdrRatesFetcher")
public class LatestIdrRatesFetcher implements DataFetcher {

    private final WebClient webClient;
    private final double spreadFactor;

    @Autowired
    public LatestIdrRatesFetcher(WebClient webClient) {
        this.webClient = webClient;
        this.spreadFactor = calculateSpreadFactor("arleyfllh");
    }

    @Override
    public List<Map<String, Object>> fetchData() {
        Map<String, Object> response = webClient.get()
                .uri("/latest?base=IDR")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        Double usdRate = rates.get("USD");

        double usdBuySpreadIdr = (1 / usdRate) * (1 + spreadFactor);

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("currency", "USD");
        data.put("rate", usdRate);
        data.put("usdBuySpreadIdr", usdBuySpreadIdr);
        result.add(data);

        return result;
    }

    private double calculateSpreadFactor(String username) {
        int total = username.toLowerCase().chars().sum();
        return (total % 1000) / 100000.0;
    }
}
