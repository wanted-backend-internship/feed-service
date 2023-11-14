package com.example.feedservice.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 공유 수 Response")
public class IncreaseShareResponse {
    @Schema(description = "게시글 공유 수", example = "100")
    private Long shareCount;
}
