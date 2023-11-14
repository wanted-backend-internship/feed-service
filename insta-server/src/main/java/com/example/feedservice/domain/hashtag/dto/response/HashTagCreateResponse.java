package com.example.feedservice.domain.hashtag.dto.response;

import com.example.feedservice.domain.hashtag.HashTag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "해시 태그 생성 Response")
public class HashTagCreateResponse {
    @Schema (description = "게시글 인덱스", example = "1")
    private Long postId;

    @Schema (description = "해시 태그 인덱스", example = "1")
    private Long hashTagId;

    @Schema (description = "해시 태그")
    private List<HashTag> hashTags;
}