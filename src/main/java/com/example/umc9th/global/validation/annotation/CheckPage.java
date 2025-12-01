package com.example.umc9th.global.validation.annotation;

import com.example.umc9th.global.validation.validator.CheckPageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckPageValidator.class) // 검증기 연결
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPage {

    String message() default "잘못된 페이지 번호입니다."; // 기본 에러 메시지
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}