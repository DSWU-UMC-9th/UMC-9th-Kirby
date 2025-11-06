package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository
        extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    // 가게 상세 > 리뷰 목록 (최신순, 페이징)
    Page<Review> findByStoreIdOrderByCreatedAtDesc(Long storeId, Pageable pageable);

    // 마이페이지 > 내가 쓴 리뷰 목록 (최신순, 페이징)
    Page<Review> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    // 가게 평균 별점 (집계)
    @Query("select avg(r.star) from Review r where r.store.id = :storeId")
    Double findAvgStarByStoreId(Long storeId);
}
