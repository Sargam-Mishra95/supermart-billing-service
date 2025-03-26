package com.retailstore.billing.factory;

import org.springframework.stereotype.Component;

@Component
public class NoDiscountStrategy implements DiscountStrategy {

    
    @Override
    public Double applyDiscount(double discountEligibleTotalAmt) {
        return discountEligibleTotalAmt;
    }
}
