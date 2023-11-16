package com.example.feedservice.domain.hashtag.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "해시 태그 생성 Request")
public class HashTagCreateRequest {
    @Schema (description = "게시글 인덱스", example = "1")
    private Long postId;

    @Schema (description = "해시 태그", example = "가방")
    private String hashTag;
}
