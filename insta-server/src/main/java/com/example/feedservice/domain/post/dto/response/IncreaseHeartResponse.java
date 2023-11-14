package com.example.feedservice.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 좋아요 수 Response")
public class IncreaseHeartResponse {
    @Schema(description = "게시글 좋아요 수", example = "100")
    private Long heartCount;
}
