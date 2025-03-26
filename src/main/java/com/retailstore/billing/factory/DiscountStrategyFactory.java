package com.retailstore.billing.factory;

import com.retailstore.billing.enums.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class DiscountStrategyFactory {

     private final EmployeeDiscountStrategy employeeDiscountStrategy;
     private final AffiliateDiscountStrategy affiliateDiscountStrategy;
     private final LoyaltyDiscountStrategy loyaltyDiscountStrategy;

     @Autowired
     public DiscountStrategyFactory(@Qualifier("employeeDiscountStrategy") EmployeeDiscountStrategy employeeDiscountStrategy,
                                    @Qualifier("affiliateDiscountStrategy") AffiliateDiscountStrategy affiliateDiscountStrategy,
                                    @Qualifier("loyaltyDiscountStrategy") LoyaltyDiscountStrategy loyaltyDiscountStrategy)
     {
         this.employeeDiscountStrategy = employeeDiscountStrategy;
         this.affiliateDiscountStrategy = affiliateDiscountStrategy;
         this.loyaltyDiscountStrategy = loyaltyDiscountStrategy;
     }

    public DiscountStrategy getDiscountStrategy(String customerType) {

        if (CustomerType.EMPLOYEE.toString().equalsIgnoreCase(customerType)) {
            return employeeDiscountStrategy;
        }else if(CustomerType.AFFILIATE.toString().equalsIgnoreCase(customerType)){
            return affiliateDiscountStrategy;
        }else if(CustomerType.LOYAL.toString().equalsIgnoreCase(customerType)){
            return loyaltyDiscountStrategy;
        }
        return new NoDiscountStrategy();
    }
}
