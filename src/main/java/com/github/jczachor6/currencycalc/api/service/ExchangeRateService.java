package com.github.jczachor6.currencycalc.api.service;

import com.github.jczachor6.currencycalc.api.exception.ExchangeRateException;
import com.github.jczachor6.currencycalc.api.model.ExchangeRate;
import com.github.jczachor6.currencycalc.api.util.CurrencyReferenceRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final static String CURRENCY_RELATION_CHART_URL = "https://www.tradingview.com/chart/?symbol=FX_IDC%3A";
    private final static int PRECISION = 5;

    private final CurrencyReferenceRateProvider ecbReferenceRateProvider;
    private final CurrencyService currencyService;

    public ExchangeRate calculate(String source, String target, BigDecimal amount) {
        Map<String, BigDecimal> rates = ecbReferenceRateProvider.loadRates();

        validateCurrencySupported(source, rates);
        validateCurrencySupported(target, rates);
        validateProperConversion(source, target);

        currencyService.incrementTotalRequestAmount(source);
        currencyService.incrementTotalRequestAmount(target);

        BigDecimal toCurrencyRate = rates.get(target);
        BigDecimal fromCurrencyRate = rates.get(source);

        BigDecimal result = toCurrencyRate
                .divide(fromCurrencyRate, PRECISION, BigDecimal.ROUND_UP)
                .multiply(amount);

        String link = CURRENCY_RELATION_CHART_URL + source + target;
        return new ExchangeRate(source, target, result, link);
    }

    private void validateProperConversion(String source, String target) {
        if (source.equalsIgnoreCase(target)) {
            throw new ExchangeRateException("Provided currencies are the same");
        }
    }

    private void validateCurrencySupported(String currency, Map<String, BigDecimal> rates) {
        if (!rates.containsKey(currency.toUpperCase())) {
            throw new ExchangeRateException(currency + " not supported");
        }
    }
}
