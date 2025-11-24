package com.example.umc9th.domain.test.controller;

import com.example.umc9th.domain.test.converter.TestConverter;
import com.example.umc9th.domain.test.dto.res.TestResDTO;
import com.example.umc9th.domain.test.service.query.TestQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TestController {

    private final TestQueryService testQueryService;

    @GetMapping("/test")
    public ApiResponse<TestResDTO.Testing> test() {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                TestConverter.toTestingDTO("This is Test!")
        );
    }

    //  예외 테스트용
    @GetMapping("/exception")
    public ApiResponse<TestResDTO.Exception> exception(@RequestParam Long flag) {

        // 여기서 flag 검사 -> 1이면 예외 던짐
        testQueryService.checkFlag(flag);

        // 예외가 안 나면 성공 응답
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK,
                TestConverter.toExceptionDTO("This is Test!")
        );
    }
}
