package com.retailstore.billing.factory;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EmployeeDiscountStrategy implements DiscountStrategy {

    @Value("${app.discounts.employeeDiscount}")
    private double employeeDiscount;


    @Override
    public Double applyDiscount(double discountEligibleTotalAmt) {
        return discountEligibleTotalAmt - (employeeDiscount / 100) * discountEligibleTotalAmt;
    }
}
