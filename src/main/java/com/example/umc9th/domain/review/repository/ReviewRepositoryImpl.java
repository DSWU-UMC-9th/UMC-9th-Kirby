package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.dto.QReviewResponse;
import com.example.umc9th.domain.review.dto.ReviewResponse;
import com.example.umc9th.domain.review.entity.QReply;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager; // ⬅ 워크북 방식에 따라 EntityManager 사용
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.umc9th.domain.member.entity.QMember.member;
import static com.example.umc9th.domain.review.entity.QReview.review;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    //  워크북 방식: EntityManager를 주입받아 QueryFactory 생성
    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ReviewResponse> findReviewsWithPagingAndDetails(Long storeId, Long lastReviewId, int size, Integer starRating) {
        QReply reply = QReply.reply;

        return queryFactory
                .select(new QReviewResponse(
                        review.id,
                        review.content,
                        review.star,
                        review.createdAt,
                        review.member.name,
                        reply.content
                ))
                .from(review)
                .join(review.member, member).fetchJoin() // Fetch Join
                .leftJoin(reply).on(reply.review.eq(review))
                .where(
                        review.store.id.eq(storeId),
                        gtReviewId(lastReviewId),
                        eqStarRating(starRating) // 별점 필터링 로직
                )
                .orderBy(review.id.desc())
                .limit(size + 1)
                .fetch();
    }

    private BooleanExpression gtReviewId(Long lastReviewId) {
        return lastReviewId == 0L ? null : review.id.lt(lastReviewId);
    }

    private BooleanExpression eqStarRating(Integer starRating) {
        if (starRating == null) { return null; }
        if (starRating == 5) { return review.star.eq(5.0f); }
        if (starRating >= 1 && starRating <= 4) {
            float start = (float) starRating;
            float end = start + 0.999f;
            return review.star.between(start, end);
        }
        return null;
    }
}