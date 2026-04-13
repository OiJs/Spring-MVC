package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.LoginRequest;
import com.nhnacademy.springmvc.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StudentLoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@CookieValue(name = "SESSION", required = false) String sessionId,
                        @ModelAttribute LoginRequest loginRequest,
                        HttpServletRequest request) {

        if (Objects.nonNull(sessionId)) {
            HttpSession session = request.getSession(false);

            if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute("studentId"))) {
                String studentId = (String) session.getAttribute("studentId");
                return "redirect:/student/" + studentId;
            }
        }
        return "login-form";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute LoginRequest loginRequest,
                          BindingResult bindingResult,
                          HttpSession session,
                          HttpServletResponse response) {

        if(bindingResult.hasErrors()) {
            return "login-form";
        }
        String id = loginRequest.getId();
        String pwd = loginRequest.getPassword();

        if(loginService.login(id, pwd)) {
            session.setAttribute("studentId", id);

            Cookie sessionCookie = new Cookie("SESSION", session.getId());
            sessionCookie.setMaxAge(60*60);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);

            return "redirect:/student/" + id;
        }
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String doLogout(HttpSession session,
                           HttpServletResponse response) {

        if(Objects.nonNull(session)){
            session.invalidate();
        }

        Cookie sessionCookie = new Cookie("SESSION", null);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
        return "redirect:/login";
    }
}
