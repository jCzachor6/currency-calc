package com.github.jczachor6.currencycalc.api.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ExchangeRate {
    String source;
    String target;
    BigDecimal result;
    String url;
}
