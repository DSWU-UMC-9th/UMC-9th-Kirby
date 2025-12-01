package com.example.umc9th.domain.review.dto;

import com.example.umc9th.domain.review.entity.Review; // Review 엔티티 import 필요
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor // 기본 생성자 추가 (JPA/JSON 변환 시 필요)
public class ReviewResponse {

    private Long reviewId;
    private String content;
    private Float star;
    private LocalDateTime createdAt;
    private String memberName;
    private String replyContent;

    // 🔥 [추가 1] 가게 이름 필드 추가 (내가 쓴 리뷰 목록에서 필요함)
    private String storeName;

    // === 1. QueryDSL에서 사용하는 생성자 (기존 유지) ===
    // 주의: 기존 QueryDSL 코드(ReviewRepositoryImpl)가 깨지지 않게 파라미터 순서를 그대로 유지해야 함!
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
        this.storeName = null; // 기존 API(가게 상세)에서는 가게 이름이 필요 없으므로 null로 둡니다.
    }

    // === 2. [추가 2] Entity -> DTO 변환 메서드 (내가 쓴 리뷰 목록용) ===
    // 이번 미션에서 Service가 이 메서드를 사용합니다.
    public static ReviewResponse from(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.reviewId = review.getId();
        dto.content = review.getContent();
        dto.star = review.getStar();
        dto.createdAt = review.getCreatedAt();
        dto.memberName = review.getMember().getName(); // 작성자 이름

        // ⭐ 여기서 가게 이름을 꺼내옵니다!
        if (review.getStore() != null) {
            dto.storeName = review.getStore().getName();
        }

        // 대댓글 로직 (Entity 구조에 따라 다를 수 있음, 일단 null 처리하거나 필요하면 로직 추가)
        dto.replyContent = null;

        return dto;
    }

    // === 게터들 ===
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

    // 🔥 [추가 3] 가게 이름 게터
    public String getStoreName() {
        return storeName;
    }

    // ====================== 페이지 DTO (기존 유지) ======================

    public static class ReviewPageDto {

        private final Long lastReviewId; // 내가 쓴 리뷰(Offset 페이징)에서는 null로 보내면 됨
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