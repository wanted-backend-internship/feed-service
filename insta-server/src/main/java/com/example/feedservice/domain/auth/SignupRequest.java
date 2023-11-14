package com.example.feedservice.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "회원가입 요청 DTO")
@Getter
@Setter
@Builder
public class SignupRequest {
    @Schema(description = "이메일", example = "test@test.com")
    private String email;

    @Schema(description = "닉네임", example = "sinishop")
    private String username;

    @Schema(description = "비밀번호")
    private String password;
}
