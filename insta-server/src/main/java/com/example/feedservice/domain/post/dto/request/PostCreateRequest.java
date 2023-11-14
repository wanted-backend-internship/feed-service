package com.example.feedservice.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema (description = "게시글 생성 Request")
public class PostCreateRequest {
    @Schema (description = "게시글 제목", example = "오늘 신상")
    private String title;

    @Schema (description = "게시글 내용", example = "홍대에서 산 가방 ...")
    private String description;

    @Schema (description = "게시글 조회수", example = "100")
    private Long viewCount;

    @Schema (description = "게시글 좋아요 수", example = "100")
    private Long heartCount;

    @Schema (description = "게시글 공유 수", example = "100")
    private Long shareCount;

    @Schema (description = "해시 태그", example = "#가방 #시은샵")
    private String hashTags;
}
