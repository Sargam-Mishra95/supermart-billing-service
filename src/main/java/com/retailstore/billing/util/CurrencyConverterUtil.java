package com.retailstore.billing.util;


import com.retailstore.billing.config.ExchangeApiConfig;
import com.retailstore.billing.response.ExchangeRateResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@Getter
public class CurrencyConverterUtil {

    @Autowired
    ExchangeApiConfig exchangeApiConfig;

    @Autowired
    RestTemplate restTemplate;


    public ResponseEntity<ExchangeRateResponse> getTargetExchangeValue(String sourceCurrency){

        try{
            String url = UriComponentsBuilder.fromUriString(exchangeApiConfig.getUrl()).buildAndExpand(sourceCurrency).toUriString();
            ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);
            return response;
        }catch(HttpClientErrorException | HttpServerErrorException e){
            throw new RuntimeException("Unable to connect to currency converter service");
        }

    }




}
