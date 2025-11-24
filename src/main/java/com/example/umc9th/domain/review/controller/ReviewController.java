package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.ReviewRequest;
import com.example.umc9th.domain.review.dto.ReviewResponse;
import com.example.umc9th.domain.review.service.ReviewCommandService;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;

@RestController
@RequestMapping("/api/stores/{storeId}/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;   // 추가

    public ReviewController(ReviewQueryService reviewQueryService,
                            ReviewCommandService reviewCommandService) {  //  생성자 수정
        this.reviewQueryService = reviewQueryService;
        this.reviewCommandService = reviewCommandService;
    }

    // 가게 리뷰 조회 (기존)
    @GetMapping
    public ResponseEntity<ReviewResponse.ReviewPageDto> getStoreReviewList(
            @PathVariable Long storeId,
            @RequestParam(name = "lastId", defaultValue = "0") Long lastId,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "star", required = false) Integer starRating
    ) {
        ReviewResponse.ReviewPageDto result =
                reviewQueryService.getStoreReviewList(storeId, lastId, size, starRating);

        return ResponseEntity.ok(result);
    }

    // 가게에 리뷰 추가하기 (새로 추가)
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createReview(
            @PathVariable Long storeId,
            @RequestBody ReviewRequest request
    ) {
        Long reviewId = reviewCommandService.createReview(storeId, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(GeneralSuccessCode.OK, reviewId));
    }
}
