package com.nhnacademy.springbootmvc.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StopWatchFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("=== stopwatch start ===");
        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            log.info("=== stopwatch end | Execution Time: {}ms ===", duration);
        }
    }
}
