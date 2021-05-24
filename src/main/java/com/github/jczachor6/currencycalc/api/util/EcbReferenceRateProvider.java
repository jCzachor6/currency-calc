package com.github.jczachor6.currencycalc.api.util;

import com.github.jczachor6.currencycalc.api.model.xml.Cube;
import com.github.jczachor6.currencycalc.api.model.xml.Envelope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EcbReferenceRateProvider implements CurrencyReferenceRateProvider {
    public static final String DEFAULT_EXCHANGE_RATE_CURRENCY = "EUR";
    private static final String ECB_RATES_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Override
    public Map<String, BigDecimal> loadRates() {
        Envelope envelope = new RestTemplate().getForObject(ECB_RATES_URL, Envelope.class);
        return Optional.ofNullable(envelope)
                .flatMap(e -> e.getData()
                .getCubeDaily()
                .stream()
                .findFirst()
                .map(daily -> daily.getCubes().stream().collect(Collectors.toMap(Cube::getCurrency, Cube::getRate)))
                .map(this::insertDefaultCurrency))
                .orElseGet(Collections::emptyMap);
    }

    private Map<String, BigDecimal> insertDefaultCurrency(Map<String, BigDecimal> ratesMap) {
        ratesMap.put(DEFAULT_EXCHANGE_RATE_CURRENCY, BigDecimal.ONE);
        return ratesMap;
    }
}
