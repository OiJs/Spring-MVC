package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentModifyRequest;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.service.StudentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/student/{studentId}/modify")
public class StudentModifyController {
    private final StudentService studentService;

    @GetMapping
    public String studentModifyForm(@PathVariable String studentId,
                                    HttpSession session,
                                    Model model) {

        String sessionStudentId = (String) session.getAttribute("studentId");
        if (Objects.isNull(sessionStudentId) || !sessionStudentId.equals(studentId)) {
            return "redirect:/login";
        }

        Student student = studentService.getStudent(studentId);
        Student maskedStudent = Student.constructPasswordMaskedUser(student);
        model.addAttribute("student", maskedStudent);

        return "modify-form";
    }

    @PostMapping
    public String doModify(@PathVariable String studentId,
                           @Valid @ModelAttribute StudentModifyRequest studentModifyRequest,
                           BindingResult bindingResult,
                           HttpSession session) {

        String sessionStudentId = (String) session.getAttribute("studentId");
        if (Objects.isNull(sessionStudentId) || !sessionStudentId.equals(studentId)) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "modify-form";
        }

        Student student = new Student(
                studentId,
                studentModifyRequest.getPassword(),
                studentModifyRequest.getName(),
                studentModifyRequest.getEmail(),
                studentModifyRequest.getScore(),
                studentModifyRequest.getComment()
        );

        studentService.modify(student);

        return "redirect:/student/" + studentId;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleStudentNotFoundException(StudentNotFoundException ex) {
        log.error("학생 조회 실패: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("errorCode", HttpStatus.NOT_FOUND.value());
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("timestamp", LocalDate.now());

        return mav;
    }
}
