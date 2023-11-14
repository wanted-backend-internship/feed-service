package com.example.feedservice.domain.post.service;

import com.example.feedservice.domain.post.PostRepository;
import com.example.feedservice.domain.post.domain.Post;
import com.example.feedservice.domain.post.dto.response.IncreaseHeartResponse;
import com.example.feedservice.domain.post.dto.response.IncreaseShareResponse;
import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountService {
    private final PostRepository postRepository;

    @Transactional
    public IncreaseShareResponse increaseShareResponse(Long posId) {
        Post post = postRepository.findById(posId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));
        post.increaseShareCount();
        postRepository.save(post);

        IncreaseShareResponse increaseShareResponse = IncreaseShareResponse.builder()
                .shareCount(post.getShareCount())
                .build();

        return increaseShareResponse;
    }

    @Transactional
    public IncreaseHeartResponse increaseHeartResponse(Long posId) {
        Post post = postRepository.findById(posId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));
        post.increaseHeartCount();
        postRepository.save(post);

        IncreaseHeartResponse increaseHeartResponse = IncreaseHeartResponse.builder()
                .heartCount(post.getShareCount())
                .build();

        return increaseHeartResponse;
    }
}
