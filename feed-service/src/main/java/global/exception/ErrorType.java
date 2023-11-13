package global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Schema(description = "Exception Type (Enum)")
@Getter
@ToString
public enum ErrorType {

    // 어세스 토큰이 만료된 경우
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN-001", "어세스 토큰이 만료되었습니다."),

    // 리프레시 토큰이 만료된 경우
    REFRESH_TOKEN_EXPIRED(HttpStatus.FORBIDDEN, "TOKEN-002", "리프레시 토큰이 만료되었습니다."),

    // 리프레시 토큰이 null 인 경우
    REFRESH_TOKEN_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "TOKEN-003", "리프레시 토큰이 존재하지 않습니다. 쿠키를 확인해 주세요."),

    // 어세스 토큰이 만료되어 인증을 진행하지 못하는 경우
    TOKEN_USER_DOES_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "TOKEN-004", "어세스 토큰 만료로 인해 유저 인증단계를 밟을 수 없습니다."),

    // 권한이 부족한 경우
    USER_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "AUTH-001", "권한이 부족하여 접근할 수 없습니다."),

    // 사용자를 찾을 수 없는 경우
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-001", "유저가 존재하지 않습니다."),

    // 이미 이메일이 존재하는 경우;
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "USER-002", "이미 존재하는 이메일 입니다."),

    // 내부 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-001", "내부 서버 오류로 인해 요청을 처리할 수 없습니다.");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;


    ErrorType(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}