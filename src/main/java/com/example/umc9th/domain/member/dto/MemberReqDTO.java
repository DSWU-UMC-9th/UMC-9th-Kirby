package com.example.umc9th.domain.member.dto;

import com.example.umc9th.domain.member.entity.Gender;
import com.example.umc9th.domain.member.entity.SocialType;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    // 회원 가입 요청 DTO
    public record JoinDTO(
            String name,
            Gender gender,
            LocalDate birth,
            String address,
            String detailAddress,
            SocialType socialType,
            String socialId,
            String email,
            String phoneNumber,
            List<Long> preferFoodIds   // 선호 음식 id 리스트 (MemberFood로 매핑 예정)
    ) {}
}
