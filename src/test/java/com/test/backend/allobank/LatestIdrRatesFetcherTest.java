package com.test.backend.allobank;

import com.test.backend.allobank.helper.LatestIdrRatesFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LatestIdrRatesFetcherTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private LatestIdrRatesFetcher fetcher;

    @BeforeEach
    public void setUp() {
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        fetcher = new LatestIdrRatesFetcher(webClient);
    }

    @Test
    public void testFetchData() {
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of("USD", 0.00006)
        );
        Mockito.when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(mockResponse));

        List<Map<String, Object>> result = fetcher.fetchData();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Map<String, Object> data = result.get(0);
        Assertions.assertEquals("USD", data.get("currency"));
        Assertions.assertEquals(0.00006, data.get("rate"));
        double spreadFactor = (1 / 0.00006) * (1 + calculateSpreadFactor("arleyfllh"));
        Assertions.assertEquals(spreadFactor, data.get("usdBuySpreadIdr"));
    }

    private double calculateSpreadFactor(String username) {
        int total = username.toLowerCase().chars().sum();
        return (total % 1000) / 100000.0;
    }
}
