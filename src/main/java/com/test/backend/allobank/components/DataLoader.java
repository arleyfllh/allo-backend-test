package com.test.backend.allobank.components;

import com.test.backend.allobank.helper.DataFetcher;
import com.test.backend.allobank.service.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataLoader {

    @Autowired
    private Map<String, DataFetcher> fetchers;

    @Autowired
    private DataStore dataStore;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            dataStore.loadData("latest_idr_rates", fetchers.get("latestIdrRatesFetcher").fetchData());
            dataStore.loadData("historical_idr_usd", fetchers.get("historicalIdrUsdFetcher").fetchData());
            dataStore.loadData("supported_currencies", fetchers.get("supportedCurrenciesFetcher").fetchData());
        };
    }
}
