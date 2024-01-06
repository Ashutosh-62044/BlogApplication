package com.blog.blogger.repository;

import com.blog.blogger.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // You can add custom query methods here if needed
}
