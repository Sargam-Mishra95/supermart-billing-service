package com.retailstore.billing.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CustomerType {

    EMPLOYEE("EMPLOYEE"), AFFILIATE("AFFILIATE"), LOYAL("LOYAL"), REGULAR("REGULAR");

    private final String value;


    @Override
    public String toString() {

        return value;
    }
}
