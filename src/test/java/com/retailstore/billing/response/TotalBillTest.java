package com.retailstore.billing.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TotalBillTest {


    @Test
    public void testGetters() {
        TotalBill totalBill = new TotalBill(100.0, 90.0, 10.0, "USD", "EUR");

        assertEquals(100.0, totalBill.getTotalAmount());
        assertEquals(90.0, totalBill.getPayableAmount());
        assertEquals(10.0, totalBill.getTotalDiscountReceived());
        assertEquals("USD", totalBill.getSourceCurrency());
        assertEquals("EUR", totalBill.getTargetCurrency());
    }

    @Test
    public void testEquals() {
        TotalBill totalBill1 = new TotalBill(100.0, 90.0, 10.0, "USD", "EUR");
        TotalBill totalBill2 = new TotalBill(100.0, 90.0, 10.0, "USD", "EUR");
        TotalBill totalBill3 = new TotalBill(200.0, 180.0, 20.0, "USD", "EUR");

        assertEquals(totalBill1, totalBill2);
        assertNotEquals(totalBill1, totalBill3);
    }

    @Test
    public void testHashCode() {
        TotalBill totalBill1 = new TotalBill(100.0, 90.0, 10.0, "USD", "EUR");
        TotalBill totalBill2 = new TotalBill(100.0, 90.0, 10.0, "USD", "EUR");

        assertEquals(totalBill1.hashCode(), totalBill2.hashCode());
    }
}
