package com.github.jczachor6.currencycalc.api.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RatesFixture {
    public static Map<String, BigDecimal> basicRates(){
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("USD", BigDecimal.valueOf(1.23));
        rates.put("EUR", BigDecimal.ONE);
        return rates;
    }
}
