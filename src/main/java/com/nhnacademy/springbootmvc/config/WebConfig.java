package com.nhnacademy.springbootmvc.config;

import com.nhnacademy.springbootmvc.converter.StringToBooleanConverter;
import com.nhnacademy.springbootmvc.converter.StringToRoleConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new StringToRoleConverter());
        //TODO 2: StringToBoolean converter 등록
        registry.addConverter(new StringToBooleanConverter());
    }
}
