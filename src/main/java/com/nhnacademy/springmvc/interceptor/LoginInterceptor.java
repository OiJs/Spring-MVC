package com.nhnacademy.springmvc.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.info("Cookies is null");
            response.sendRedirect("/login");
            return false;
        }
        Optional<Cookie> sessionCookie = Arrays.stream(cookies)
                .filter(cookie -> "SESSION".equals(cookie.getName()))
                .findFirst();

        if (sessionCookie.isEmpty()) {
            log.info("sessionCookie is null");
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
