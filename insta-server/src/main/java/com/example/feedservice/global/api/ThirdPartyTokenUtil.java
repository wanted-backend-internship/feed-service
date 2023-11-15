package com.example.feedservice.global.api;

import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ErrorType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThirdPartyTokenUtil {
    @Value("${secret.key}")
    private String SECRET_KEY;

    public String createToken() {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expMillis = nowMillis + 600000; // 10 분 후 만료
        Date exp = new Date(expMillis);

        Random rand = new Random();
        int randomNumber = 100000 + rand.nextInt(900000);

        return Jwts.builder()
                .setSubject(String.valueOf(randomNumber))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 토큰의 유효성 검사
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (ExpiredJwtException e) { // 어세스 토큰 만료
            throw new ApiException(ErrorType.ACCESS_TOKEN_EXPIRED);
        }
    }

    // 어세스 토큰을 헤더에서 추출하는 메서드
    public String getJWTTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Instagram");
        log.info("====================================================");
        log.info("Instagram Header: " + authorizationHeader); // 로그 추가
        log.info("====================================================");

        if (authorizationHeader != null) {
            return authorizationHeader;
        }
        return null;
    }
}
