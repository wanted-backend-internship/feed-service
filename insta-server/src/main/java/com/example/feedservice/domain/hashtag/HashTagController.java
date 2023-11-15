package com.example.feedservice.domain.hashtag;

import com.example.feedservice.domain.hashtag.dto.request.HashTagCreateRequest;
import com.example.feedservice.domain.hashtag.dto.request.HashTagDeleteRequest;
import com.example.feedservice.domain.hashtag.dto.response.HashTagCreateResponse;
import com.example.feedservice.domain.post.dto.response.PostResponse;
import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ApiExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "HashTag", description = "해시태그 API")
@RestController
@RequestMapping(value = "/api/posts/hash-tags")
@RequiredArgsConstructor
public class HashTagController {
    private final HashTagService hashTagService;

    @Operation(summary = "해시태그 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해시태그 생성 성공",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping(value = "")
    public ResponseEntity<?> createPost(@RequestBody HashTagCreateRequest hashTagCreateRequest, HttpServletRequest httpServletRequest) {
        try {
            HashTagCreateResponse hashTagCreateResponse = hashTagService.createHashTag(hashTagCreateRequest, httpServletRequest);
            return ResponseEntity.ok().body(hashTagCreateResponse);

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }

    @Operation(summary = "해시태그 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해시태그 삭제 성공",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "해시태그를 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @DeleteMapping(value = "")
    public ResponseEntity<?> createPost(@RequestBody HashTagDeleteRequest hashTagDeleteRequest, HttpServletRequest httpServletRequest) {
        try {
            hashTagService.deleteHashTag(hashTagDeleteRequest, httpServletRequest);
            return ResponseEntity.ok().body("해시 태그 삭제 성공");

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }
}
