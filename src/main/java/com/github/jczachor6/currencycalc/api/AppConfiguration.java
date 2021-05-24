package com.github.jczachor6.currencycalc.api;

import com.github.jczachor6.currencycalc.api.util.CurrencyReferenceRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {
    private final CurrencyReferenceRateProvider currencyReferenceRateProvider;

    @Bean
    public Map<String, Integer> currencyRequestedAmount() {
        Map<String, Integer> currencyRequestedAmount = new HashMap<>();
        currencyReferenceRateProvider.loadRates().forEach((name, rate) -> currencyRequestedAmount.put(name, 0));
        return currencyRequestedAmount;
    }
}
