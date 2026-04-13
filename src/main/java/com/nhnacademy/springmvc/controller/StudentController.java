package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
            throw new StudentNotFoundException();
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
            throw new StudentNotFoundException();
        }

        ModelAndView mav = new ModelAndView("student-view");
        mav.addObject("student", loginUser);
        mav.addObject("hideScore", true);

        return mav;
    }
}
