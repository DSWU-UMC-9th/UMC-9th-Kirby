package com.example.umc9th.domain.store.validation.validator;

import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.domain.store.validation.annotation.ExistStore;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class StoreExistValidator implements ConstraintValidator<ExistStore, Long> {

    private final StoreRepository storeRepository;

    @Override
    public void initialize(ExistStore constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        // 가게 ID가 존재하는지 DB에서 확인
        boolean exists = storeRepository.existsById(value);

        if (!exists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(GeneralErrorCode.NOT_FOUND.toString())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}