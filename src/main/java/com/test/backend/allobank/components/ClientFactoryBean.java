package com.test.backend.allobank.components;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientFactoryBean implements FactoryBean<WebClient> {
    @Value("${frankfurter.base.url}")
    private String baseUrl;

    @Override
    public @Nullable WebClient getObject() throws Exception {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public @Nullable Class<?> getObjectType() {
        return WebClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
