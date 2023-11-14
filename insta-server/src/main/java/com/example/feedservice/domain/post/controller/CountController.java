package com.example.feedservice.domain.post.controller;

import com.example.feedservice.domain.post.dto.response.IncreaseHeartResponse;
import com.example.feedservice.domain.post.dto.response.IncreaseShareResponse;
import com.example.feedservice.domain.post.dto.response.PostResponse;
import com.example.feedservice.domain.post.service.CountService;
import com.example.feedservice.global.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class CountController {
    private final CountService countService;

    @Operation(summary = "좋아요 수 증가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 수 증가 성공",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping(value = "/{postId}/hearts")
    public ResponseEntity<?> increaseHeartResponse(@PathVariable Long postId) {
        try {
            IncreaseHeartResponse increaseHeartResponse = countService.increaseHeartResponse(postId);
            return ResponseEntity.ok().body(increaseHeartResponse);

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }

    @Operation(summary = "공유 수 증가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공유 수 증가 성공",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping(value = "/{postId}/shares")
    public ResponseEntity<?> increaseShareResponse(@PathVariable Long postId) {
        try {
            IncreaseShareResponse increaseShareResponse = countService.increaseShareResponse(postId);
            return ResponseEntity.ok().body(increaseShareResponse);

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }
}
