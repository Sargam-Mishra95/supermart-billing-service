package com.retailstore.billing.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalBill {

    private Double totalAmount;
    private Double payableAmount;
    private Double totalDiscountReceived;
    private String sourceCurrency;
    private String targetCurrency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalBill totalBill = (TotalBill) o;
        return Objects.equals(totalAmount, totalBill.totalAmount) && Objects.equals(payableAmount, totalBill.payableAmount) && Objects.equals(totalDiscountReceived, totalBill.totalDiscountReceived) && Objects.equals(sourceCurrency, totalBill.sourceCurrency) && Objects.equals(targetCurrency, totalBill.targetCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalAmount, payableAmount, totalDiscountReceived, sourceCurrency, targetCurrency);
    }
}
