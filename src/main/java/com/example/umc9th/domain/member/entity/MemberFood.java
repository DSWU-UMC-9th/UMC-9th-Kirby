package com.example.umc9th.domain.member.entity;

import com.example.umc9th.domain.food.entity.Food;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "member_food",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_food_member_food",
                        columnNames = {"member_id", "food_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_food_id")
    private Long id;

    // 회원 - N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 음식 - N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
