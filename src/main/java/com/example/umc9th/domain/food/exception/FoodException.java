package com.example.umc9th.domain.food.exception;

import com.example.umc9th.domain.food.exception.code.FoodErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;

public class FoodException extends GeneralException {

    public FoodException(FoodErrorCode code) {
        super(code);
    }
}
