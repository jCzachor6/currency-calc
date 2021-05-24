package com.github.jczachor6.currencycalc.api.controller;

import com.github.jczachor6.currencycalc.api.model.Currency;

import java.util.Arrays;
import java.util.List;

public class CurrencyFixture {

    public static List<Currency> supportedCurrencies(){
        return Arrays.asList(
                new Currency("EUR", 1),
                new Currency("USD", 4)
        );
    }

}
