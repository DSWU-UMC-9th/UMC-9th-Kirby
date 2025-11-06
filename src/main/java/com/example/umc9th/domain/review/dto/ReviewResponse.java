package com.example.umc9th.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponse {

    private Long reviewId;
    private String content;
    private Float star;
    private LocalDateTime createdAt;
    private String memberName;
    private String replyContent;

    // QueryDSL에서 사용할 생성자
    @QueryProjection
    public ReviewResponse(Long reviewId,
                          String content,
                          Float star,
                          LocalDateTime createdAt,
                          String memberName,
                          String replyContent) {
        this.reviewId = reviewId;
        this.content = content;
        this.star = star;
        this.createdAt = createdAt;
        this.memberName = memberName;
        this.replyContent = replyContent;
    }

    // === 게터들 명시적으로 작성 (Lombok 안 믿고 IDE 확실하게 인식시키자) ===
    public Long getReviewId() {
        return reviewId;
    }

    public String getContent() {
        return content;
    }

    public Float getStar() {
        return star;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    // ====================== 페이지 DTO ======================

    public static class ReviewPageDto {

        private final Long lastReviewId;
        private final boolean hasNext;
        private final int totalElements;
        private final List<ReviewResponse> reviewList;

        public ReviewPageDto(Long lastReviewId,
                             boolean hasNext,
                             int totalElements,
                             List<ReviewResponse> reviewList) {
            this.lastReviewId = lastReviewId;
            this.hasNext = hasNext;
            this.totalElements = totalElements;
            this.reviewList = reviewList;
        }

        public Long getLastReviewId() {
            return lastReviewId;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public List<ReviewResponse> getReviewList() {
            return reviewList;
        }
    }
}
