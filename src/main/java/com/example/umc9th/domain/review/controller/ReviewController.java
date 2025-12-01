package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.ReviewRequest;
import com.example.umc9th.domain.review.dto.ReviewResponse;
import com.example.umc9th.domain.review.service.ReviewCommandService;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.validation.annotation.CheckPage; // 👈 만든 어노테이션 import
import com.example.umc9th.domain.store.validation.annotation.ExistStore; // 👈 가게 존재 검증 어노테이션 import
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated; // 👈 이거 필수!
import org.springframework.web.bind.annotation.*;

@RestController
@Validated // 1. 이 어노테이션이 있어야 @CheckPage가 동작합니다!
@RequestMapping("/api/stores/{storeId}/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    public ReviewController(ReviewQueryService reviewQueryService,
                            ReviewCommandService reviewCommandService) {
        this.reviewQueryService = reviewQueryService;
        this.reviewCommandService = reviewCommandService;
    }

    // 가게 리뷰 조회 API
    @GetMapping
    @Operation(summary = "가게 리뷰 목록 조회 API", description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작, 10개씩 조회)"), // 설명 수정
    })
    public ApiResponse<ReviewResponse.ReviewPageDto> getStoreReviewList(
            @ExistStore @PathVariable(name = "storeId") Long storeId, // 가게 존재 검증 (덤으로 추가)
            @CheckPage @RequestParam(name = "page") Integer page // 2. 여기에 @CheckPage 붙이기! (Integer 타입이어야 함)
    ) {
        // service 메서드도 page를 받는 걸로 수정되어야 합니다! (page - 1 처리)
        // 예: reviewQueryService.getStoreReviewList(storeId, page - 1);

        // 일단은 기존 메서드 호출 부분 주석 처리 해둘게요 (서비스도 수정하셔야 해서!)
        // ReviewResponse.ReviewPageDto result = reviewQueryService.getStoreReviewList(storeId, page);

        return null; // 서비스 수정 후 연결하세요!
    }

    // 가게에 리뷰 추가하기 API
    @PostMapping
    @Operation(summary = "가게 리뷰 작성 API", description = "가게에 새로운 리뷰를 작성합니다.")
    public ApiResponse<Long> createReview(
            @ExistStore @PathVariable(name = "storeId") Long storeId, // 가게 존재 검증
            @RequestBody @Valid ReviewRequest request
    ) {
        Long reviewId = reviewCommandService.createReview(storeId, request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, reviewId);
    }
}