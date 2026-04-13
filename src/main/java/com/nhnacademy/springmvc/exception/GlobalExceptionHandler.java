package com.nhnacademy.springmvc.exception;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ModelAndView handleStudentNotFoundException(StudentNotFoundException ex) {
        log.error("학생 조회 실패: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("timestamp", LocalDate.now());

        return mav;
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public String handleStudentAlreadyExistsException(StudentAlreadyExistsException ex, Model model) {
        log.error("중복 등록 시도: {}", ex.getMessage());
        model.addAttribute("errorMessage", "이미 존재하는 학생 ID입니다.");
        model.addAttribute("timestamp", LocalDate.now());

        return "register-form";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("서버 내부 오류 발생", ex);
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("timestamp", LocalDate.now());

        return "error/500";
    }
}