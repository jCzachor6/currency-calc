package com.github.jczachor6.currencycalc.api.controller;

import com.github.jczachor6.currencycalc.api.model.ExchangeRate;

import java.math.BigDecimal;

public class ExchangeRateFixture {
    public static ExchangeRate usdEurRate(){
        return new ExchangeRate("USD", "EUR", BigDecimal.valueOf(0.85), "http:/link.com/");
    }
}
