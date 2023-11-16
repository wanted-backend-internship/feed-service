package com.example.feedservice.domain.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "이메일", example = "test@test.com")
    private String email;

    @Schema(description = "비밀번호")
    private String password;
}
