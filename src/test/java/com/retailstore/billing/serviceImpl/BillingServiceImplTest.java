package com.retailstore.billing.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailstore.billing.config.ProductConfig;
import com.retailstore.billing.factory.DiscountStrategyFactory;
import com.retailstore.billing.request.BillRequest;
import com.retailstore.billing.response.ExchangeRateResponse;
import com.retailstore.billing.response.TotalBill;
import com.retailstore.billing.service.BillingServiceImpl;
import com.retailstore.billing.util.CurrencyConverterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BillingServiceImplTest {

    @Autowired
    private BillingServiceImpl billingService;

    @Mock
    private ProductConfig productConfig;

    @Mock
    private DiscountStrategyFactory discountStrategyFactory;

    @Mock
    private CurrencyConverterUtil currencyConverterUtil;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    private BillRequest getBillRequestFromFile() throws IOException {
        return objectMapper.readValue(new ClassPathResource("jsons/BillRequest.json").getFile(), BillRequest.class);
    }


    @Test
    public void testCalculateBill_CustomerType_Employee_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        TotalBill expectedTotalBill = new TotalBill(15279.0, 13723.32, 1555.68, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("electronics", "clothing"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }

    @Test
    public void testCalculateBill_CustomerType_Affiliate_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("AFFILIATE");
        TotalBill expectedTotalBill = new TotalBill(15279.0, 14251.14, 1027.86, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("electronics", "clothing"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }


    @Test
    public void testCalculateBill_CustomerType_Loyal_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("LOYAL");
        TotalBill expectedTotalBill = new TotalBill(15279.0, 14385.41, 893.59, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("electronics", "clothing"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }


    @Test
    public void testCalculateBill_CustomerType_New_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("NEW");
        TotalBill expectedTotalBill = new TotalBill(15279.0, 14515.05, 763.95, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("electronics", "clothing"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }



    @Test
    public void testCalculateBill_Currency_Converter_Fail() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("NEW");
        TotalBill expectedTotalBill = new TotalBill(15279.0, 14515.05, 763.95, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("electronics", "clothing"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("false", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }

}
