package com.nhnacademy.springmvc.service;

import com.nhnacademy.springmvc.domain.Student;

public interface StudentService {
    Student getStudent(String id);
    void register(Student student);
    void modify(Student student);
}
