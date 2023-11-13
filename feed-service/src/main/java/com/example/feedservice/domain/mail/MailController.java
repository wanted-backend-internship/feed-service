package com.example.feedservice.domain.mail;

import com.example.feedservice.global.exception.ApiExceptionResponse;
import com.example.feedservice.global.util.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Mail", description = "메일 서비스 API")
@RestController
@RequestMapping(value = "/api/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private RedisUtil redisUtil;

    @Operation(summary = "메일로 인증번호 보내기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 보내기 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저가 존재하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping
    public void getRandomNumberApi(@RequestBody MailDTO mailDTO) {
        String email = mailDTO.getEmail();
        String randomNumber;

        if (mailService.checkIsAuthCode(email) == null) {
            randomNumber = mailService.getRandomNumber();
            redisUtil.setDataExpireWithPrefix("authCode", email, randomNumber, Duration.ofMinutes(30));
            mailService.validateSetAuthCode(email);

        } else {
            randomNumber = mailService.checkIsAuthCode(email);
        }

        mailService.sendSimpleMessage(email, "이메일 인증 번호 발송", "6 자리 숫자: " + randomNumber);
    }
}
