package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.service.StudentService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @ModelAttribute("loginUser")
    public Student getLoginUser(HttpSession session) {
        String studentId = (String) session.getAttribute("studentId");
        if (studentId != null) {
            try {
                return Student.constructPasswordMaskedUser(studentService.getStudent(studentId));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    //ModelMap
    @GetMapping(value = "/student/{studentId}", params = "!hideScore")
    public String studentView(@PathVariable String studentId,
                              ModelMap modelMap,
                              @ModelAttribute("loginUser") Student loginUser) {

        if (loginUser == null || !loginUser.getId().equals(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        modelMap.addAttribute("student", loginUser);
        modelMap.addAttribute("hideScore", false);
        return "student-view";
    }

    //ModelAndView
    @GetMapping(value = "/student/{studentId}", params = "hideScore=yes")
    public ModelAndView hideView(@PathVariable String studentId,
                                 @ModelAttribute("loginUser") Student loginUser) {

        if (loginUser == null || !loginUser.getId().equals(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        ModelAndView mav = new ModelAndView("student-view");
        mav.addObject("student", loginUser);
        mav.addObject("hideScore", true);

        return mav;
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
