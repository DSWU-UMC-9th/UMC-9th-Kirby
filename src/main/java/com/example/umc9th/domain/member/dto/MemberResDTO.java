package com.example.umc9th.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    // 회원 가입 응답 DTO
    @Builder
    public record JoinDTO(
            Long memberId,
            LocalDateTime createdAt
    ) {}
}
