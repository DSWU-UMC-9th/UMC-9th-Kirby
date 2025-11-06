package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.ReviewResponse;
import com.example.umc9th.domain.review.service.ReviewQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores/{storeId}/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;


    public ReviewController(ReviewQueryService reviewQueryService) {
        this.reviewQueryService = reviewQueryService;
    }

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
}
