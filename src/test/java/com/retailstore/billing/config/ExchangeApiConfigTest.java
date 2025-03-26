package com.retailstore.billing.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExchangeApiConfigTest {
    @Autowired
    private ExchangeApiConfig exchangeApiConfig;

    @Test
    public void testUrlProperty() {
        String expectedUrl = "https://v6.exchangerate-api.com/v6/f7223a9c53816ce40c071c05/latest/{base_currency}";
        assertEquals(expectedUrl, exchangeApiConfig.getUrl());
    }
}
