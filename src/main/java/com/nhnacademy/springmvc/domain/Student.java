package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Student {
    private final String id;
    private final String password;
    private final String name;
    private final String email;
    private final int score;
    private final String comment;

    private static final String MASK = "*****";

    public static Student constructPasswordMaskedUser(Student student) {
        if (student == null) {
            return null;
        }
        return new Student(student.getId(),
                MASK,
                student.getName(),
                student.getEmail(),
                student.getScore(),
                student.getComment()
        );
    }
}
