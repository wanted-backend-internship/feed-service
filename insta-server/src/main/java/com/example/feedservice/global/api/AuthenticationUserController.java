package com.example.feedservice.global.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ThirdParty", description = "외부 요청 인증 API")
@RestController
@RequestMapping(value = "/api/auth/third")
@RequiredArgsConstructor
public class AuthenticationUserController {
    private final ThirdPartyTokenUtil thirdPartyTokenUtil;

    @GetMapping(value = "")
    public ResponseEntity<?> authorizeThirdPartyUser(
            HttpServletRequest request, HttpServletResponse response) {
        String accessToken = thirdPartyTokenUtil.createToken();
        response.setHeader("Instagram", accessToken);

        // 인증 성공 후 저장된 리디렉트 URL로 리디렉트
        String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();
    }
}