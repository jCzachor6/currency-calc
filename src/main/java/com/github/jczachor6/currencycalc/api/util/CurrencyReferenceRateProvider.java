package com.github.jczachor6.currencycalc.api.util;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyReferenceRateProvider {
    Map<String, BigDecimal> loadRates();
}
