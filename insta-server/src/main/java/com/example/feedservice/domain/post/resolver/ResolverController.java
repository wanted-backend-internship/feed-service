package com.example.feedservice.domain.post.resolver;

import com.example.feedservice.domain.post.resolver.dto.reponse.ResolverResponse;
import com.example.feedservice.domain.post.resolver.dto.request.StatisticRequest;
import com.example.feedservice.global.api.ThirdPartyTokenUtil;
import com.example.feedservice.global.exception.ApiExceptionResponse;
import com.example.feedservice.global.log.HttpRequestLog;
import com.example.feedservice.global.log.HttpRequestLogRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
            @ApiResponse(responseCode = "200", description = "요청 게시글 응답 성공",
                    content = @Content(schema = @Schema(implementation = ResolverResponse.class))),
            @ApiResponse(responseCode = "401", description = "권한 오류",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @GetMapping(value = "")
    public ResponseEntity<?> getPostByTypeAndDate(
            @Parameter(name = "hashTag", description = "유저 닉네임", in = ParameterIn.QUERY)
            @RequestParam String hashTag,
            @Parameter(name = "type", description = "date, hour", in = ParameterIn.QUERY)
            @RequestParam String type,
            @Parameter(name = "start", description = "시작 날짜", in = ParameterIn.QUERY)
            @RequestParam LocalDateTime start,
            @Parameter(name = "end", description = "끝 날짜", in = ParameterIn.PATH)
            @RequestParam LocalDateTime end,
            @Parameter(name = "value", description = "count, view_count, heart_count, share_count", in = ParameterIn.PATH)
            @RequestParam String value,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        HttpRequestLog httpRequestLog = HttpRequestLog.builder()
                .host(httpServletRequest.getRemoteHost())
                .clientIp(httpServletRequest.getRemoteAddr())
                .requestUri(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .referer(String.valueOf(httpServletRequest.getRequestURL())).build();

        httpRequestLogRepository.save(httpRequestLog);

        try {
            String accessToken = thirdPartyTokenUtil.getJWTTokenFromHeader(httpServletRequest);
            thirdPartyTokenUtil.isValidToken(accessToken);

            StatisticRequest statisticRequest = StatisticRequest.builder()
                    .hashTag(hashTag)
                    .values(value)
                    .types(type)
                    .startDate(start)
                    .endDate(end)
                    .build();


            if (type.equals("date")) {
                List<ResolverResponse> DateResponses = resolverRepository.findPostsByTypeDateAndValue(statisticRequest);
                return ResponseEntity.ok().body(DateResponses);

            } else if (type.equals("hour")) {
                List<Object[]> hourlyResponses = resolverRepository.findPostsByDateAndHour(statisticRequest);
                return ResponseEntity.ok().body(hourlyResponses);
            }
            return ResponseEntity.ok().body("조건에 맞는 게시글이 존재하지 않습니다.");

        } catch (ExpiredJwtException jwtException) {
            return ResponseEntity.ok().body("http://localhost:8082/api/auth/third");

        } catch(IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.internalServerError().body("잘못된 요청 입니다.");
        }
    }
}
