package com.example.feedservice.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 삭제 Request")
public class PostDeleteRequest {
    @Schema (description = "게시글 인덱스", example = "1")
    private Long id;
}
