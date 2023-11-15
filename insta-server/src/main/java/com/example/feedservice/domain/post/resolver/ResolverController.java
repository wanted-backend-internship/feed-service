package com.example.feedservice.domain.post.resolver;

import com.example.feedservice.domain.post.dto.response.PostCreateResponse;
import com.example.feedservice.domain.post.resolver.dto.reponse.ResolverResponse;
import com.example.feedservice.domain.post.resolver.dto.request.StatisticRequest;
import com.example.feedservice.global.api.ThirdPartyTokenUtil;
import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ApiExceptionResponse;
import com.example.feedservice.global.log.HttpRequestLog;
import com.example.feedservice.global.log.HttpRequestLogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Resolver", description = "외부 게시글 요청 응답 API")
@RestController
@RequestMapping(value = "/api/resolve/posts")
@RequiredArgsConstructor
public class ResolverController {
    private final ResolverRepository resolverRepository;
    private final ThirdPartyTokenUtil thirdPartyTokenUtil;
    private final HttpRequestLogRepository httpRequestLogRepository;

    @Operation(summary = "요청 게시글 응답")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",
                    content = @Content(schema = @Schema(implementation = PostCreateResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping(value = "")
    public ResponseEntity<?> getPostByTypeAndDate(
            @RequestParam String type,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam String value,
            HttpServletRequest httpServletRequest) {

        HttpRequestLog httpRequestLog = HttpRequestLog.builder()
                .host(httpServletRequest.getRemoteHost())
                .clientIp(httpServletRequest.getRemoteAddr())
                .requestUri(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .referer(String.valueOf(httpServletRequest.getRequestURL())).build();

        httpRequestLogRepository.save(httpRequestLog);

        try {
            String accessToken = thirdPartyTokenUtil.getJWTTokenFromHeader(httpServletRequest);

            if (!thirdPartyTokenUtil.isValidToken(accessToken)) {
                // 인증 실패: 클라이언트를 인증 URL로 리디렉트
                String redirectUrl = "http://localhost:8080/api/auth/third";
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", redirectUrl)
                        .build();
            }

            StatisticRequest statisticRequest = StatisticRequest.builder()
                    .values(value)
                    .types(type)
                    .startDate(start)
                    .endDate(end)
                    .build();


            if (type.equals("date")) {
                List<ResolverResponse> DateResponses = resolverRepository.findPostsByTypeDateAndValue(statisticRequest);
                return ResponseEntity.ok().body(DateResponses);

            } else if (type.equals("hour")) {
                List<Object[]> hourlyResponses = resolverRepository.findPostsByTypeHourAndValue(statisticRequest);
                return ResponseEntity.ok().body(hourlyResponses);
            }
            return ResponseEntity.ok().body("조건에 맞는 게시글이 존재하지 않습니다.");

        } catch (ApiException apiException) {
            return ResponseEntity.status(apiException.getErrorType().getStatus())
                    .body(apiException.getErrorType().getMessage());
        }
    }
}
