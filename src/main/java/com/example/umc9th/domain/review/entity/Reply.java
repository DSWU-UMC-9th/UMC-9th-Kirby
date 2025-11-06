package com.example.umc9th.domain.review.entity;

import com.example.umc9th.domain.member.entity.Member;
import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    // FK: review_id (댓글이 달린 리뷰)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    @Getter(AccessLevel.PUBLIC)
    private Review review;

    // FK: member_id (댓글 작성자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Getter(AccessLevel.PUBLIC)
    private Member member;
}
