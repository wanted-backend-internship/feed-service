package com.example.feedservice.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 수정 Request")
public class PostUpdateRequest {
    @Schema (description = "게시글 인덱스", example = "1")
    private Long id;

    @Schema (description = "게시글 제목", example = "오늘 신상")
    private String title;

    @Schema (description = "게시글 내용", example = "홍대에서 산 가방 ...")
    private String description;
}
