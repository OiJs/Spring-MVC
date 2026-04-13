package com.nhnacademy.springmvc.service.impl;

import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final StudentRepository studentRepository;

    @Override
    public boolean login(String id, String password) {
        return studentRepository.matches(id, password);
    }
}