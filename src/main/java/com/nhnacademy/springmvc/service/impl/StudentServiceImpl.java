package com.nhnacademy.springmvc.service.impl;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    @Override
    public Student getStudent(String id) {
        if(id == null || id.isBlank()) {
            throw new IllegalArgumentException("id is null");
        }
        return studentRepository.getStudent(id);
    }

    @Override
    public void register(Student student) {
        if(student == null) {
            throw new IllegalArgumentException("student is null");
        }
        studentRepository.register(student);
    }

    @Override
    public void modify(Student student) {
        if(student == null) {
            throw new IllegalArgumentException("student is null");
        }
        studentRepository.modify(student);
    }
}
