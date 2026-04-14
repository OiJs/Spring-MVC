package com.nhnacademy.springbootmvc.repository;

import com.nhnacademy.springbootmvc.domain.Post;

public interface PostRepository {
    boolean exists(long id);

    Post register(String title, String content);

    Post getPost(long id);

    Post modify(long id, String title, String content);

}
