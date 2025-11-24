package com.example.umc9th.domain.member.exception.code;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "해당 사용자를 찾을 수 없습니다"
    ),

    DUPLICATE_SOCIAL_MEMBER(
            HttpStatus.CONFLICT,
            "MEMBER409_1",
            "이미 가입된 회원입니다"
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
