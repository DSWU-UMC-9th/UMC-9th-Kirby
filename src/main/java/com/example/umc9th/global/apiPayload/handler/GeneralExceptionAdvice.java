package com.example.umc9th.global.apiPayload.handler;

import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionAdvice {

    //  우리가 만든 GeneralException(=도메인 커스텀 예외) 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(GeneralException ex) {

        BaseErrorCode code = ex.getCode();

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(
                        code,
                        null    // result는 이번 미션에선 null
                ));
    }

    //  그 외 모든 예외 처리 (null pointer 등 예상 못 한 것들)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {

        BaseErrorCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(code.getStatus())
                .body(ApiResponse.onFailure(
                        code,
                        ex.getMessage()   // result에 에러 메시지 한 번 담아볼 수 있음
                ));
    }
}
