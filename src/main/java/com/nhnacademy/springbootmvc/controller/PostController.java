package com.nhnacademy.springbootmvc.controller;

import com.nhnacademy.springbootmvc.domain.Post;
import com.nhnacademy.springbootmvc.domain.PostModifyRequest;
import com.nhnacademy.springbootmvc.exception.PostNotFoundException;
import com.nhnacademy.springbootmvc.exception.ValidationFailedException;
import com.nhnacademy.springbootmvc.repository.PostRepository;
import com.nhnacademy.springbootmvc.validator.PostModifyRequestValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostRepository postRepository;
    private final PostModifyRequestValidator validator;

    public PostController(PostRepository postRepository, PostModifyRequestValidator validator) {
        this.postRepository = postRepository;
        this.validator = validator;
    }

    @ModelAttribute("post")
    public Post getPost(@PathVariable("postId") long postId) {
        return postRepository.getPost(postId);
    }

    @GetMapping("/{postId}")
    public String viewPost(@ModelAttribute("post") Post post,
                           @PathVariable("postId") long postId,
                           ModelMap modelMap) {
        if (Objects.isNull(post)) {
            modelMap.put("exception", new PostNotFoundException());
            return "error";
        }

        modelMap.put("post", post);
        return "postView";
    }

    @GetMapping("/{postId}/modify")
    public String postModifyForm(@ModelAttribute("post") Post post,
                                 Model model) {
        if (Objects.isNull(post)) {
            model.addAttribute("exception", new PostNotFoundException());
            return "error";
        }

        model.addAttribute("post", post);
        return "postModify";
    }

    @PostMapping("/{postId}/modify")
    public String doModify(@PathVariable("postId") long postId,
                           @Valid @ModelAttribute PostModifyRequest request,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        postRepository.modify(postId, request.getTitle(), request.getContent());

        return "redirect:/post/" + postId;
    }

    @InitBinder("postModifyRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}
