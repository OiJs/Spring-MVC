package com.nhnacademy.springmvc.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginRequest {
    @NotBlank
    String id;

    @NotBlank
    String password;
}