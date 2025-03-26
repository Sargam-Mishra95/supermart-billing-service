package com.retailstore.billing.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.products")
public class ProductConfig {

    @Value("discountProductCategory")
    List<String> discountProductCategory;

    public List<String> getDiscountProductCategory() {
        return discountProductCategory;
    }
}
