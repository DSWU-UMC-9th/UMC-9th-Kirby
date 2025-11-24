package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewRequest;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public ReviewCommandService(
            ReviewRepository reviewRepository,
            StoreRepository storeRepository,
            MemberRepository memberRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        this.memberRepository = memberRepository;
    }

    public Long createReview(Long storeId, ReviewRequest request) {

        // 1. 스토어 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // 2. 로그인 기능 없으니까, DB에 있는 아무 회원 한 명 사용
        Member member = memberRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 3. 리뷰 엔티티 생성
        Review review = ReviewConverter.toReview(request, store, member);

        // 4. 저장
        reviewRepository.save(review);
        return review.getId();
    }
}
