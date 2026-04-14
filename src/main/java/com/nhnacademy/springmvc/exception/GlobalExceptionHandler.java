package com.nhnacademy.springmvc.exception;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("존재하지 않는 리소스: {}", ex.getMessage());
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("errorCode", HttpStatus.NOT_FOUND.value());
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("timestamp", LocalDate.now());

        return mav;
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleStudentAlreadyExistsException(StudentAlreadyExistsException ex, Model model) {
        log.error("중복 등록 시도: {}", ex.getMessage());
        model.addAttribute("errorCode", HttpStatus.CONFLICT.value());
        model.addAttribute("errorMessage", "이미 존재하는 학생 ID입니다.");
        model.addAttribute("timestamp", LocalDate.now());

        return "register-form";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        log.error("서버 내부 오류 발생", ex);
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("timestamp", LocalDate.now());

        return "error/500";
    }
}