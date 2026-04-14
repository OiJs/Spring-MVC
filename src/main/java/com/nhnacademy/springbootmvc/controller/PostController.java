package com.nhnacademy.springbootmvc.controller;

import com.nhnacademy.springbootmvc.domain.Post;
import com.nhnacademy.springbootmvc.exception.PostNotFoundException;
import com.nhnacademy.springbootmvc.repository.PostRepository;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // TODO #2: 게시물 조회 구현
    @GetMapping("/{postId}")
    public String viewPost(@PathVariable long postId, Model model) {
        Post post = postRepository.getPost(postId);

        if(post == null) {
            throw new PostNotFoundException("post not found");
        }

        model.addAttribute("post", post);
        // ...
        return "postView";
    }

    // TODO #3: 게시물 수정 form 구현
    @GetMapping("/{postId}/modify")
    public String postModifyForm() {
        // ...
        return "postModify";
    }
}
