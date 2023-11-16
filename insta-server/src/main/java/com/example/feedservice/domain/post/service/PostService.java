package com.example.feedservice.domain.post.service;

import com.example.feedservice.domain.hashtag.HashTag;
import com.example.feedservice.domain.hashtag.HashTagRepository;
import com.example.feedservice.domain.post.PostRepository;
import com.example.feedservice.domain.post.domain.Post;
import com.example.feedservice.domain.post.domain.PostEditor;
import com.example.feedservice.domain.post.dto.request.PostCreateRequest;
import com.example.feedservice.domain.post.dto.request.PostDeleteRequest;
import com.example.feedservice.domain.post.dto.request.PostUpdateRequest;
import com.example.feedservice.domain.post.dto.response.PostResponse;
import com.example.feedservice.domain.user.User;
import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ErrorType;
import com.example.feedservice.global.util.AuthUtil;
import com.example.feedservice.global.util.PageUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final AuthUtil authUtil;

    public PageUtil<PostResponse> getPosts (Pageable pageable, HttpServletRequest request) {
        Long userId = authUtil.getLoginUserIdFromToken(request);
        Page<Post> posts = postRepository.findByUserIdOrderByIdDesc(userId, pageable);
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            List<String[]> hashTags = new ArrayList<>();
            List<HashTag> temps = post.getHashTags();
            for (HashTag temp : temps) {
                hashTags.add(new String[]{String.valueOf(temp.getId()), temp.getHashTag()});
            }

            PostResponse postResponse = PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .hashTags(hashTags)
                    .heartCount(post.getHeartCount())
                    .shareCount(post.getShareCount())
                    .viewCount(post.getViewCount())
                    .createdAt(LocalDateTime.now())
                    .build();
            postResponses.add(postResponse);
        }
        return new PageUtil<>(postResponses, pageable, posts.getTotalPages());
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest postCreateRequest) {
        User user = authUtil.getLoginUser()
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        Post createPost = Post.builder()
                .title(postCreateRequest.getTitle())
                .description(postCreateRequest.getDescription())
                .shareCount(0L)
                .viewCount(0L)
                .heartCount(0L)
                .hashTags(new ArrayList<>())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(createPost);

        String[] words = postCreateRequest.getHashTags().split(" ");
        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {
                word = word.substring(1);

                HashTag hashTag = HashTag.builder()
                        .hashTag(word)
                        .build();
                hashTag.mappingPost(createPost);
                hashTagRepository.save(hashTag);
            }
        }

        List<String[]> hashTags = new ArrayList<>();
        for (HashTag temp : createPost.getHashTags()) {
            hashTags.add(new String[]{String.valueOf(temp.getId()), temp.getHashTag()});
        }

        PostResponse postResponse = PostResponse.builder()
                .id(createPost.getId())
                .title(createPost.getTitle())
                .description(createPost.getDescription())
                .hashTags(hashTags)
                .heartCount(createPost.getHeartCount())
                .shareCount(createPost.getShareCount())
                .viewCount(createPost.getViewCount())
                .createdAt(createPost.getCreatedAt())
                .build();

        return postResponse;
    }


    @Transactional
    public void updatePost (PostUpdateRequest postUpdateRequest, HttpServletRequest httpServletRequest) {
        Long userId = authUtil.getLoginUserIdFromToken(httpServletRequest);
        Post post = postRepository.findById(postUpdateRequest.getId())
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        if (userId.equals(post.getUser().getId())) {
            PostEditor.PostEditorBuilder postEditorBuilder = post.toUpdate();
            PostEditor postEditor = postEditorBuilder
                    .title(postUpdateRequest.getTitle())
                    .description(postUpdateRequest.getDescription())
                    .build();

            post.update(postEditor);
        }
    }

    @Transactional
    public void deletePost (PostDeleteRequest postDeleteRequest, HttpServletRequest httpServletRequest) {
        Long userId = authUtil.getLoginUserIdFromToken(httpServletRequest);
        Post post = postRepository.findById(postDeleteRequest.getId())
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        if (userId.equals(post.getUser().getId())) {
            postRepository.delete(post);
        }
    }

    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        post.increaseViewCount();
        postRepository.save(post);

        List<String[]> hashTags = new ArrayList<>();
        List<HashTag> temps = post.getHashTags();
        for (HashTag temp : temps) {
            hashTags.add(new String[]{String.valueOf(temp.getId()), temp.getHashTag()});
        }

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .hashTags(hashTags)
                .heartCount(post.getHeartCount())
                .shareCount(post.getShareCount())
                .viewCount(post.getViewCount())
                .build();

        return postResponse;
    }
}
