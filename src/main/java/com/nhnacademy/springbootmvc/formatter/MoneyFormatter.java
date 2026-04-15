package com.nhnacademy.springbootmvc.formatter;

import java.text.DecimalFormat;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

@Component
public class MoneyFormatter implements Formatter<BigDecimal> {
    //TODO 1: BigDecimal <-> String 변환하는 formatter 구현
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        Number number = formatter.parse(text);
        return new BigDecimal(number.toString());
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        return formatter.format(object);
    }

}
