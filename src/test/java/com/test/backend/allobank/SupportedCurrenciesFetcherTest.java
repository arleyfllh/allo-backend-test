package com.test.backend.allobank;

import com.test.backend.allobank.helper.SupportedCurrenciesFetcher;
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
public class SupportedCurrenciesFetcherTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private SupportedCurrenciesFetcher fetcher;

    @BeforeEach
    public void setUp() {
        Mockito.when(webClient.get()).thenReturn(requestHeadersUriSpec);
        Mockito.when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        fetcher = new SupportedCurrenciesFetcher(webClient);
    }

    @Test
    public void testFetchData() {
        Map<String, Object> mockResponse = Map.of(
                "USD", "United States Dollar",
                "EUR", "Euro"
        );
        Mockito.when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(mockResponse));

        List<Map<String, Object>> result = fetcher.fetchData();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(d -> "USD".equals(d.get("currency"))));
    }
}
