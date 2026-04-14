package com.nhnacademy.springmvc.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String id) {
        super("존재하지 않는 학생입니다: " + id);
    }
}
