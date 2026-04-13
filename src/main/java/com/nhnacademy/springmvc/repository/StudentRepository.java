package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;

public interface StudentRepository {
    boolean exists(String id);
    Student register(Student student);
    Student modify(Student student);
    Student getStudent(String id);
    boolean matches(String id, String password);
}
