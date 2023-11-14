package com.example.feedservice.domain.post;

import com.example.feedservice.domain.post.dto.request.PostCreateRequest;
import com.example.feedservice.domain.post.dto.request.PostDeleteRequest;
import com.example.feedservice.domain.post.dto.response.PostCreateResponse;
import com.example.feedservice.domain.post.dto.response.PostResponse;
import com.example.feedservice.global.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 리스트 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 리스트 반환 성공",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @GetMapping(value = "")
    public ResponseEntity<?> getPosts(Pageable pageable, HttpServletRequest request) {
        try {
             PageUtil<PostResponse> posts = postService.getPosts(pageable, request);
            return ResponseEntity.ok().body(posts);

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }

    @Operation(summary = "게시글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",
            content = @Content(schema = @Schema(implementation = PostCreateResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
            content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PostMapping(value = "")
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequest request) {
        try {
            PostCreateResponse postCreateResponse = postService.createPost(request);
            return ResponseEntity.ok().body(postCreateResponse);

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }

    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @PatchMapping(value = "")
    public ResponseEntity<?> updatePost(@RequestBody PostCreateRequest request) {
        try {
            postService.createPost(request);
            return ResponseEntity.ok().body("게시글 수정 성공!");

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없는 경우",
                    content = @Content(schema = @Schema(implementation = ApiException.class)))
    })
    @DeleteMapping(value = "")
    public ResponseEntity<?> deletePost(@RequestBody PostDeleteRequest request, HttpServletRequest httpServletRequest) {
        try {
            postService.deletePost(request, httpServletRequest);
            return ResponseEntity.ok().body("게시글 삭제 성공!");

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }
}
