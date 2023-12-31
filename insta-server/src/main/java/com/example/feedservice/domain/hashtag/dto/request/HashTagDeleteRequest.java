package com.example.feedservice.domain.hashtag.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "해시 태그 삭제 Request")
public class HashTagDeleteRequest {
    @Schema (description = "해시 태그 인덱스", example = "1")
    private String hashTagId;
}
