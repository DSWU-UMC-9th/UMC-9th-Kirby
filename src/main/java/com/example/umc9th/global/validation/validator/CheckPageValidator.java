package com.example.umc9th.global.validation.validator;

import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.validation.annotation.CheckPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckPageValidator implements ConstraintValidator<CheckPage, Integer> {

    @Override
    public void initialize(CheckPage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 1. null 이거나 0 이하(1보다 작음)인 경우 에러 처리
        if (value == null || value < 1) {
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(GeneralErrorCode.BAD_REQUEST.toString())
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}