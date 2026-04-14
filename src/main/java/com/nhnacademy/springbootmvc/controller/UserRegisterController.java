package com.nhnacademy.springbootmvc.controller;

import com.nhnacademy.springbootmvc.domain.User;
import com.nhnacademy.springbootmvc.domain.UserRegisterRequest;
import com.nhnacademy.springbootmvc.exception.ValidationFailedException;
import com.nhnacademy.springbootmvc.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/register")
public class UserRegisterController {
    private final UserRepository userRepository;

    public UserRegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userRegisterForm() {
        return "userRegister";
    }

    @PostMapping
    public ModelAndView registerUser(@Valid @ModelAttribute UserRegisterRequest userRequest,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        User user = userRepository.addUser(userRequest.getId(), userRequest.getPassword(),
                                           userRequest.getAge(), userRequest.getName());

        ModelAndView mav = new ModelAndView("user");
        mav.addObject("user", User.constructPasswordMaskedUser(user));

        return mav;
    }
}
