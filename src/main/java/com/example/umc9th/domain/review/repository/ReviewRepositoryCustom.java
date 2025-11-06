package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewRepositoryCustom {

    /**
     * 가게별 리뷰 목록 조회 (페이징 + 별점 필터)
     * @param storeId 가게 ID
     * @param lastReviewId 마지막으로 조회한 리뷰 ID (커서 역할)
     * @param size 페이지 크기
     * @param starRating 별점 필터 (null이면 전체 조회)
     * @return ReviewResponse 리스트
     */
    List<ReviewResponse> findReviewsWithPagingAndDetails(Long storeId, Long lastReviewId, int size, Integer starRating);
}