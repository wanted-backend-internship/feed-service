package com.example.feedservice.domain.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "메일 요청 DTO")
@Getter
@NoArgsConstructor
public class MailDTO {

    @Schema(description = "이메일", example = "test@test.com")
    private String email;
}