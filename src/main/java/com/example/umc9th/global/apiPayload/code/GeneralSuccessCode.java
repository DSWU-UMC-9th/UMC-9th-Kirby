package com.example.umc9th.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "COMMON200",
            "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED,
            "COMMON201",
            "리소스가 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT,
            "COMMON204",
            "내용이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
