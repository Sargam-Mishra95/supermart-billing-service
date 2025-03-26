package com.retailstore.billing.service;
import com.retailstore.billing.request.BillRequest;
import com.retailstore.billing.response.TotalBill;

public interface BillingService {

    public TotalBill calculateBill(BillRequest billRequest);

}
