package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.ReviewResponse;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    // 명시적 생성자 (Lombok 없이)
    public ReviewQueryService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewResponse.ReviewPageDto getStoreReviewList(
            Long storeId,
            Long lastReviewId,
            int size,
            Integer starRating
    ) {
        // 커스텀 QueryDSL 메서드 호출
        List<ReviewResponse> reviewList =
                reviewRepository.findReviewsWithPagingAndDetails(storeId, lastReviewId, size, starRating);

        boolean hasNext = reviewList.size() > size;
        if (hasNext) {
            // size + 1 개를 가져왔으면, 마지막 하나 잘라서 "다음 페이지 있음" 표시
            reviewList.remove(size);
        }

        Long nextLastReviewId = null;
        if (!reviewList.isEmpty()) {
            ReviewResponse last = reviewList.get(reviewList.size() - 1);
            nextLastReviewId = last.getReviewId();   // ← 아까 에러 나던 부분
        }


        return new ReviewResponse.ReviewPageDto(
                nextLastReviewId,
                hasNext,
                reviewList.size(),
                reviewList
        );
    }
}
