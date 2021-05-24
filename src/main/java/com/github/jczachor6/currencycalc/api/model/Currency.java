package com.github.jczachor6.currencycalc.api.model;

import lombok.Value;

@Value
public class Currency {
    String name;
    Integer totalRequestedAmount;
}
