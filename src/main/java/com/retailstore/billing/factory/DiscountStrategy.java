package com.retailstore.billing.factory;

public interface DiscountStrategy {

    public Double applyDiscount(double discountEligibleTotalAmt);
}
