package com.nhnacademy.springbootmvc.config;

import com.nhnacademy.springbootmvc.converter.StringToRoleConverter;
import com.nhnacademy.springbootmvc.formatter.LocalDateFormatter;
import com.nhnacademy.springbootmvc.formatter.MoneyFormatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new StringToRoleConverter());
        registry.addFormatter(new LocalDateFormatter());
        //TODO 2: MoneyFormatter 등록
        registry.addFormatter(new MoneyFormatter());
    }
}
