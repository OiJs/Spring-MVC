package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.service.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentService studentService;

    @GetMapping
    public String studentRegisterForm(@ModelAttribute StudentRegisterRequest studentRegisterRequest) {
        return "register-form";
    }

    @PostMapping
    public String doRegister(@Valid @ModelAttribute StudentRegisterRequest studentRegisterRequest,
                             BindingResult bindingResult,
                             HttpSession session,
                             HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            return "register-form";
        }
        Student student = new Student(
                studentRegisterRequest.getId(),
                studentRegisterRequest.getPassword(),
                studentRegisterRequest.getName(),
                studentRegisterRequest.getEmail(),
                studentRegisterRequest.getScore(),
                studentRegisterRequest.getComment()
        );
        studentService.register(student);

        session.setAttribute("studentId", student.getId());

        Cookie sessionCookie = new Cookie("SESSION", session.getId());
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(60*60);
        sessionCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);

        return "redirect:/student/" + student.getId();
    }
}
