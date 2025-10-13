package com.example.umc9th.domain.user.entity;

import com.example.umc9th.domain.food.entity.Food;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "user_flavor",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_flavor_user_food",
                        columnNames = {"user_id", "food_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserFlavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_flavor_id")
    private Long id;

    // 유저 - N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 음식 - N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
