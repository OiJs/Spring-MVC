package com.nhnacademy.springmvc.repository.impl;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<String, Student> studentMap = new HashMap<>();

    public StudentRepositoryImpl() {

        studentMap.put("tom", new Student("tom", "1234", "톰", "tom@nhnacademy.com", 100, "good"));
        studentMap.put("jake", new Student("jake", "5678", "제이크", "jake@nhnacademy.com", 90, "good"));
    }

    @Override
    public boolean exists(String id) {
        validateId(id);
        return studentMap.containsKey(id);
    }

    @Override
    public Student register(Student student) {
        if (Objects.isNull(student)) {
            throw new IllegalArgumentException("student is null");
        }

        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student modify(Student student) {
        if(Objects.isNull(student)) {
            throw new IllegalArgumentException("student is null");
        }
        if(!exists(student.getId())) {
            throw new StudentNotFoundException(student.getId());
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudent(String id) {
        validateId(id);

        Student student = studentMap.get(id);

        if(student == null) {
            throw new StudentNotFoundException(id);
        }
        return student;
    }

    @Override
    public boolean matches(String id, String password) {
        validateId(id);

        return Optional.ofNullable(studentMap.get(id))
                .map(s -> s.getPassword().equals(password))
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id is null");
        }
    }
}
