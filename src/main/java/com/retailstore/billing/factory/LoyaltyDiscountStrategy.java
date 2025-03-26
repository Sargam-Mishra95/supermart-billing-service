package com.retailstore.billing.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyDiscountStrategy implements DiscountStrategy{

    @Value("${app.discounts.loyaltyDiscount}")
    private double loyaltyDiscount;
    @Override
    public Double applyDiscount(double discountEligibleTotalAmt) {
        return discountEligibleTotalAmt - (loyaltyDiscount / 100) * discountEligibleTotalAmt;
    }
}
