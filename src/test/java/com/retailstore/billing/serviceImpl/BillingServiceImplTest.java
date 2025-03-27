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
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BillingServiceImplTest {


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

    private TotalBill getBillResponseFromFile() throws IOException {
        return objectMapper.readValue(new ClassPathResource("jsons/BillResponse.json").getFile(), TotalBill.class);
    }


    @Test
    void testCalculateBill_CustomerType_Employee_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        TotalBill expectedTotalBill = getBillResponseFromFile();

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("Electronics", "Dairy", "Fashion"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }

    @Test
    void testCalculateBill_CustomerType_Affiliate_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("AFFILIATE");
        TotalBill expectedTotalBill = new TotalBill(15331.8, 14300.39, 1031.41, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("Electronics", "Dairy", "Fashion"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }


    @Test
    void testCalculateBill_CustomerType_Loyal_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("LOYAL");
        TotalBill expectedTotalBill = new TotalBill(15331.8, 14435.12, 896.68, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("Electronics", "Dairy", "Fashion"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }


    @Test
    void testCalculateBill_CustomerType_New_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        billRequest.setCustomerType("NEW");
        TotalBill expectedTotalBill = new TotalBill(15331.8, 14565.21, 766.59, "USD", "EUR");

        when(productConfig.getDiscountProductCategory()).thenReturn(List.of("Electronics", "Dairy", "Fashion"));
        when(discountStrategyFactory.getDiscountStrategy(any())).thenReturn(discountEligibleTotalAmt -> discountEligibleTotalAmt * 0.9);
        when(currencyConverterUtil.getTargetExchangeValue(any())).thenReturn(new ResponseEntity<>(new ExchangeRateResponse("success", Map.of("EUR", 0.9)), HttpStatus.OK));
        TotalBill actualTotalBill = billingService.calculateBill(billRequest);
        assertEquals(expectedTotalBill, actualTotalBill);
    }



}
