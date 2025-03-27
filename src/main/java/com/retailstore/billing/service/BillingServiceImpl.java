package com.retailstore.billing.service;

import com.retailstore.billing.config.ProductConfig;
import com.retailstore.billing.factory.DiscountStrategy;
import com.retailstore.billing.factory.DiscountStrategyFactory;
import com.retailstore.billing.request.BillRequest;
import com.retailstore.billing.request.ProductRequest;
import com.retailstore.billing.response.ExchangeRateResponse;
import com.retailstore.billing.response.TotalBill;
import com.retailstore.billing.util.CurrencyConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class BillingServiceImpl implements BillingService {


    @Value("${app.discounts.per100Discount}")
    private Integer per100Discount;

    @Autowired
    public ProductConfig productConfig;
    @Autowired
    public
    DiscountStrategyFactory discountStrategyFactory;

    @Autowired
    public
    CurrencyConverterUtil currencyConverterUtil;



    public TotalBill calculateBill(BillRequest billRequest) throws HttpClientErrorException,HttpServerErrorException  {

        double discountProdAmount = 0;
        double nonDiscountProdAmount = 0;

        for(ProductRequest product : billRequest.getProductRequest()) {
            if (productConfig.getDiscountProductCategory().contains(product.getProductCategory().toLowerCase())) {
                discountProdAmount = discountProdAmount + product.getQuantity() * product.getProductPrice();
            } else {
                nonDiscountProdAmount = nonDiscountProdAmount + product.getQuantity() * product.getProductPrice();
            }
        }
        double totalAmount = discountProdAmount + nonDiscountProdAmount;
        DiscountStrategy discountStrategy = discountStrategyFactory.getDiscountStrategy(billRequest.getCustomerType());
        discountProdAmount =  discountStrategy.applyDiscount(discountProdAmount);
        double amtPostPercentageDiscount = discountProdAmount + nonDiscountProdAmount;
        double totalPayableAmount = amtPostPercentageDiscount - (((int)amtPostPercentageDiscount / 100) * per100Discount);


        return getTotalBill(billRequest,totalAmount, totalPayableAmount);
    }

    private TotalBill getTotalBill(BillRequest billRequest, double totalAmount, double totalPayableAmount) {

        ExchangeRateResponse response = currencyConverterUtil.getTargetExchangeValue(billRequest.getOriginalCurrency()).getBody();
        Double conversionRate = response.getConversion_rates().get(billRequest.getTargetCurrency());
        return new TotalBill(getRoundFigure(totalAmount*conversionRate),getRoundFigure((totalPayableAmount*conversionRate)), getRoundFigure(((totalAmount-totalPayableAmount)*conversionRate)),billRequest.getOriginalCurrency(),billRequest.getTargetCurrency() );

    }

    private Double getRoundFigure(double price) {
        return Math.round((price) * 100.0) / 100.0;
    }
}
