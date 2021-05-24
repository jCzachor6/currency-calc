package com.github.jczachor6.currencycalc.api.controller;

import com.github.jczachor6.currencycalc.api.model.Currency;
import com.github.jczachor6.currencycalc.api.model.ExchangeRate;
import com.github.jczachor6.currencycalc.api.service.CurrencyService;
import com.github.jczachor6.currencycalc.api.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ExchangeRateController.class)
@ContextConfiguration(classes = {ExchangeRateController.class})
class ExchangeRateControllerTest {
    @MockBean
    private ExchangeRateService exchangeRateService;
    @MockBean
    private CurrencyService currencyService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnExchangeRate_defaultAmount() throws Exception {
        String source = "USD";
        String target = "EUR";
        BigDecimal amount = BigDecimal.valueOf(1);

        when(exchangeRateService.calculate(source, target, amount)).thenReturn(ExchangeRateFixture.usdEurRate());

        mockMvc.perform(get("/api/exchange-rate?source=USD&target=EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("USD"))
                .andExpect(jsonPath("$.target").value("EUR"))
                .andExpect(jsonPath("$.result").value("0.85"))
                .andExpect(jsonPath("$.url").value("http:/link.com/"));

        verify(exchangeRateService).calculate(source, target, amount);
    }

    @Test
    public void shouldReturnExchangeRate_providedAmount() throws Exception {
        String source = "USD";
        String target = "EUR";
        BigDecimal amount = BigDecimal.valueOf(12.34);

        when(exchangeRateService.calculate(source, target, amount)).thenReturn(ExchangeRateFixture.usdEurRate());

        mockMvc.perform(get("/api/exchange-rate?source=USD&target=EUR&amount=12.34"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("USD"))
                .andExpect(jsonPath("$.target").value("EUR"))
                .andExpect(jsonPath("$.result").value("0.85"))
                .andExpect(jsonPath("$.url").value("http:/link.com/"));

        verify(exchangeRateService).calculate(source, target, amount);
    }

    @Test
    public void shouldReturnSupportedCurrencies() throws Exception {
        when(currencyService.getSupportedCurrencies()).thenReturn(CurrencyFixture.supportedCurrencies());

        mockMvc.perform(get("/api/exchange-rate/supported-currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("EUR"))
                .andExpect(jsonPath("$.[0].totalRequestedAmount").value("1"))
                .andExpect(jsonPath("$.[1].name").value("USD"))
                .andExpect(jsonPath("$.[1].totalRequestedAmount").value("4"));

        verify(currencyService).getSupportedCurrencies();
    }

}
