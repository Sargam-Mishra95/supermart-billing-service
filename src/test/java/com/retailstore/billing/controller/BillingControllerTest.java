package com.retailstore.billing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailstore.billing.request.BillRequest;
import com.retailstore.billing.response.TotalBill;
import com.retailstore.billing.service.BillingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BillingControllerTest {

    @Mock
    private BillingService billingService;

    @InjectMocks
    private BillingController billingController;


    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    private BillRequest getBillRequestFromFile() throws IOException {
        File file = new File("src/test/resources/jsons/BillRequest.json");
        return objectMapper.readValue(file, BillRequest.class);
    }

    @Test
    void testCalculateBill_Success() throws Exception {
        BillRequest billRequest = getBillRequestFromFile();
        TotalBill totalBill = new TotalBill(15279.00, 13723.32, 1555.68, "USD", "EUR");
        when(billingService.calculateBill(any(BillRequest.class))).thenReturn(totalBill);
        ResponseEntity<?> response = billingController.calculateBill(billRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(totalBill, response.getBody());
    }

    @Test
    void testCalculateBill_ClientError() throws Exception {
        BillRequest billRequest = new BillRequest();
        when(billingService.calculateBill(any(BillRequest.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Client error"));
        ResponseEntity<?> response = billingController.calculateBill(billRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("400 Client error", response.getBody());
    }

    @Test
    void testCalculateBill_ServerError() throws Exception {
        BillRequest billRequest = new BillRequest();

        when(billingService.calculateBill(any(BillRequest.class))).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error"));
        ResponseEntity<?> response = billingController.calculateBill(billRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("500 Server error", response.getBody());
    }

    @Test
    void testCalculateBill_UnexpectedError() throws Exception {
        BillRequest billRequest = new BillRequest();
        when(billingService.calculateBill(any(BillRequest.class))).thenThrow(new RuntimeException("An unexpected error occurred while creating the bill"));
        ResponseEntity<?> response = billingController.calculateBill(billRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred while creating the bill", response.getBody());
    }

}
