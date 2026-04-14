package com.nhnacademy.springbootmvc.validator;

import com.nhnacademy.springbootmvc.domain.PostModifyRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PostModifyRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostModifyRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title", "400", "title is empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "400", "content is empty");
    }
}
