package com.github.jczachor6.currencycalc.api.service;

import com.github.jczachor6.currencycalc.api.exception.ExchangeRateException;
import com.github.jczachor6.currencycalc.api.model.ExchangeRate;
import com.github.jczachor6.currencycalc.api.util.CurrencyReferenceRateProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {
    @Mock
    private CurrencyReferenceRateProvider ecbReferenceRateProvider;
    @Mock
    private CurrencyService currencyService;
    @InjectMocks
    private ExchangeRateService subject;

    @Test
    public void shouldThrowExchangeRateException_sourceCurrencyNotSupported() {
        when(ecbReferenceRateProvider.loadRates()).thenReturn(RatesFixture.basicRates());

        assertThrows(ExchangeRateException.class, () -> {
            subject.calculate("PLN", "USD", BigDecimal.ONE);
        });
    }

    @Test
    public void shouldThrowExchangeRateException_targetCurrencyNotSupported() {
        when(ecbReferenceRateProvider.loadRates()).thenReturn(RatesFixture.basicRates());

        assertThrows(ExchangeRateException.class, () -> {
            subject.calculate("USD", "PLN", BigDecimal.ONE);
        });
    }

    @Test
    public void shouldThrowExchangeRateException_wrongConversion() {
        when(ecbReferenceRateProvider.loadRates()).thenReturn(RatesFixture.basicRates());

        assertThrows(ExchangeRateException.class, () -> {
            subject.calculate("USD", "USD", BigDecimal.ONE);
        });
    }

    @Test
    public void shouldIncrementTotalRequestAmount() {
        when(ecbReferenceRateProvider.loadRates()).thenReturn(RatesFixture.basicRates());

        subject.calculate("EUR", "USD", BigDecimal.ONE);

        verify(currencyService).incrementTotalRequestAmount("EUR");
        verify(currencyService).incrementTotalRequestAmount("USD");
    }

    @Test
    public void shouldCalculateExchangeRate() {
        when(ecbReferenceRateProvider.loadRates()).thenReturn(RatesFixture.basicRates());

        ExchangeRate result = subject.calculate("EUR", "USD", BigDecimal.ONE);

        assertEquals(result, new ExchangeRate("EUR", "USD", BigDecimal.valueOf(1.23).setScale(5), "https://www.tradingview.com/chart/?symbol=FX_IDC%3AEURUSD"));
    }
}
