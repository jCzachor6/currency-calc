package com.github.jczachor6.currencycalc.api.service;

import com.github.jczachor6.currencycalc.api.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final Map<String, Integer> currencyRequestedAmount;

    public List<Currency> getSupportedCurrencies() {
        return currencyRequestedAmount
                .entrySet()
                .stream()
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void incrementTotalRequestAmount(String currency) {
        int requestedAmount = currencyRequestedAmount.containsKey(currency) ? currencyRequestedAmount.get(currency) + 1 : 1;
        currencyRequestedAmount.put(currency, requestedAmount);
    }
}
