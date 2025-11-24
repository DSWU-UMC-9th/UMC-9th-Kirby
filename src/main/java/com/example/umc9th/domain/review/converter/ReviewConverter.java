package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.dto.ReviewRequest;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Store;

public class ReviewConverter {

    public static Review toReview(ReviewRequest request, Store store, Member member) {
        return Review.builder()
                .content(request.getContent())
                .star(request.getStar())
                .store(store)
                .member(member)
                .build();
    }
}
