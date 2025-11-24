package com.example.umc9th.domain.test.converter;

import com.example.umc9th.domain.test.dto.res.TestResDTO;

public class TestConverter {

    // 성공 응답용 DTO
    public static TestResDTO.Testing toTestingDTO(String testing) {
        return TestResDTO.Testing.builder()
                .testString(testing)
                .build();
    }

    // 예외 상황 응답용 DTO
    public static TestResDTO.Exception toExceptionDTO(String testing) {
        return TestResDTO.Exception.builder()
                .testString(testing)
                .build();
    }
}
