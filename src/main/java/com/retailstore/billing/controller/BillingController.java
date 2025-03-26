package com.retailstore.billing.controller;


import com.retailstore.billing.request.BillRequest;
import com.retailstore.billing.response.TotalBill;
import com.retailstore.billing.service.BillingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@SecurityRequirement(name = "basicAuth")
@RequestMapping("/api")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateBill(@RequestBody BillRequest billRequest){
        try{
            TotalBill bill = billingService.calculateBill(billRequest);
            return ResponseEntity.status(HttpStatus.OK).body( bill);
        }catch(HttpClientErrorException | HttpServerErrorException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }
}
