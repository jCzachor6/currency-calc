package com.github.jczachor6.currencycalc.api.controller;

import com.github.jczachor6.currencycalc.api.model.Currency;
import com.github.jczachor6.currencycalc.api.model.ExchangeRate;
import com.github.jczachor6.currencycalc.api.service.CurrencyService;
import com.github.jczachor6.currencycalc.api.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    private final CurrencyService currencyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeRate getSupportedCurrencies(@RequestParam String source,
                                               @RequestParam String target,
                                               @RequestParam(defaultValue = "1") BigDecimal amount) {
        return exchangeRateService.calculate(source, target, amount);
    }

    @GetMapping(value = "/supported-currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Currency> getSupportedCurrencies() {
        return currencyService.getSupportedCurrencies();
    }
}
