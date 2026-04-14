package com.nhnacademy.springbootmvc.config;

import com.nhnacademy.springbootmvc.filter.StopWatchFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<StopWatchFilter> stopWatchFilter() {
        FilterRegistrationBean<StopWatchFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new StopWatchFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
