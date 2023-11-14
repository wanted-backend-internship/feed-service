package com.example.feedservice.domain.post;

import com.example.feedservice.domain.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByUserId(Long userId);
    Optional<Post> findById(Long userId);
    Page<Post> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
