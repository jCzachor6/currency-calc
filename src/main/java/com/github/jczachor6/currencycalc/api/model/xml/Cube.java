package com.github.jczachor6.currencycalc.api.model.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;


@Setter
@Getter
@XmlRootElement(name = "Cube")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cube {
    @XmlElement
    private String currency;

    @XmlElement
    private BigDecimal rate;
}


