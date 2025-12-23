package com.test.backend.allobank;

import com.test.backend.allobank.helper.HistoricalIdrUsdFetcher;
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
public class HistoricalIdrUsdFetcherTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private HistoricalIdrUsdFetcher fetcher;

    @BeforeEach
    public void setUp() {
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        fetcher = new HistoricalIdrUsdFetcher(webClient);
    }

    @Test
    public void testFetchData() {
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of(
                        "2023-12-29", Map.of("USD", 0.000065),
                        "2024-01-02", Map.of("USD", 0.000064)
                )
        );
        Mockito.when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(mockResponse));

        List<Map<String, Object>> result = fetcher.fetchData();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Map<String, Object> data = result.get(0);
        Assertions.assertTrue(data.containsKey("date"));
        Assertions.assertTrue(data.containsKey("rate"));
        Assertions.assertEquals(0.000065, data.get("rate"));
    }
}
