package com.nhnacademy.springmvc.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class StudentModifyRequest {
    @NotBlank
    String password;

    @NotBlank
    String name;

    @Email
    String email;

    @NotNull
    @Min(0)
    @Max(100)
    Integer score;

    @Length(max = 200)
    String comment;
}
