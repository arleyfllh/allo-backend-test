package com.test.backend.allobank.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("supportedCurrenciesFetcher")
public class SupportedCurrenciesFetcher implements DataFetcher {

    private final WebClient webClient;

    public SupportedCurrenciesFetcher(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Map<String, Object>> fetchData() {
        Map<String, Object> response = webClient.get()
                .uri("/currencies")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map<String, Object>> result = new ArrayList<>();
        response.forEach((currency, description) -> {
            Map<String, Object> data = new HashMap<>();
            data.put("currency", currency);
            data.put("description", description);
            result.add(data);
        });

        return result;
    }
}
