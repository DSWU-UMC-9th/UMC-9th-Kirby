package com.example.umc9th.domain.food.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    // ERD가 Enum로 보였으니 Enum 사용 권장 (문자열 저장)
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    private FoodName name;
}
