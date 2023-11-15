package com.example.feedservice.domain.hashtag;

import com.example.feedservice.domain.hashtag.dto.request.HashTagCreateRequest;
import com.example.feedservice.domain.hashtag.dto.request.HashTagDeleteRequest;
import com.example.feedservice.domain.hashtag.dto.response.HashTagCreateResponse;
import com.example.feedservice.domain.post.PostRepository;
import com.example.feedservice.domain.post.domain.Post;
import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ErrorType;
import com.example.feedservice.global.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    private final PostRepository postRepository;
    private final AuthUtil authUtil;

    @Transactional
    public HashTagCreateResponse createHashTag(HashTagCreateRequest hashTagCreateRequest, HttpServletRequest httpServletRequest) {
        Post post =  postRepository.findById(hashTagCreateRequest.getPostId())
                .orElseThrow(() -> new ApiException(ErrorType.POST_NOT_FOUND));

        Long userId = authUtil.getLoginUserIdFromToken(httpServletRequest);
        HashTag hashTag = null;
        if (userId.equals(post.getUser().getId())) {
            hashTag = HashTag.builder()
                    .hashTag(hashTagCreateRequest.getHashTag())
                    .post(post)
                    .build();
            hashTagRepository.save(hashTag);
        }

        List<String[]> hashTags = new ArrayList<>();
        List<HashTag> temps = post.getHashTags();
        for (HashTag temp : temps) {
            hashTags.add(new String[]{String.valueOf(temp.getId()), temp.getHashTag()});
        }

        HashTagCreateResponse hashTagCreateResponse = HashTagCreateResponse.builder()
                .hashTagId(String.valueOf(hashTag.getId()))
                .postId(post.getId())
                .hashTags(hashTags)
                .build();

        return hashTagCreateResponse;
    }

    @Transactional
    public void deleteHashTag(HashTagDeleteRequest hashTagDeleteRequest, HttpServletRequest httpServletRequest) {
        HashTag hashTag = hashTagRepository.findById(Long.parseLong(hashTagDeleteRequest.getHashTagId()))
                .orElseThrow(() -> new ApiException(ErrorType.HASH_TAG_NOT_FOUND));

        Long loginUserId = authUtil.getLoginUserIndex();
        Long userId = authUtil.getLoginUserIdFromToken(httpServletRequest);
        if (loginUserId.equals(userId)) {
            hashTagRepository.delete(hashTag);
        }
    }
}
