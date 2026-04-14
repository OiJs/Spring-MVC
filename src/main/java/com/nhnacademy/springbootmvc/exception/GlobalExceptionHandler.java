package com.nhnacademy.springbootmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PostNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        model.addAttribute("code", HttpStatus.NOT_FOUND.value());
        return "error";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyException(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        model.addAttribute("code", HttpStatus.CONFLICT.value());
        return "error";
    }
}
