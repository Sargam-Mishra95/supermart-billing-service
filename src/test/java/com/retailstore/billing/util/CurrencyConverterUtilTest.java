package com.retailstore.billing.util;

import com.retailstore.billing.config.ExchangeApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CurrencyConverterUtilTest {


    @Mock
    private ExchangeApiConfig exchangeApiConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyConverterUtil currencyConverterUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTargetExchangeValue_ClientError() {
        when(exchangeApiConfig.getUrl()).thenReturn("https://example.com/{sourceCurrency}");
        when(restTemplate.getForEntity(anyString(), any(Class.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThrows(RuntimeException.class, () -> {
            currencyConverterUtil.getTargetExchangeValue("USD");
        });
    }
}
