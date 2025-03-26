package com.retailstore.billing.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AffiliateDiscountStrategy implements DiscountStrategy{

    @Value("${app.discounts.affiliateDiscount}")
    private double affiliateDiscount;
    @Override
    public Double applyDiscount(double discountEligibleTotalAmt) {
        return  discountEligibleTotalAmt - (affiliateDiscount / 100) * discountEligibleTotalAmt;
    }
}
