package com.example.umc9th.domain.member.converter;

import com.example.umc9th.domain.member.dto.MemberReqDTO;
import com.example.umc9th.domain.member.dto.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;

public class MemberConverter {

    // Entity -> DTO
    public static MemberResDTO.JoinDTO toJoinDTO(Member member) {
        return MemberResDTO.JoinDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    // DTO -> Entity
    public static Member toMember(MemberReqDTO.JoinDTO dto) {
        return Member.builder()
                .name(dto.name())
                .gender(dto.gender())
                .birth(dto.birth())
                .address(dto.address())
                .detailAddress(dto.detailAddress())
                .socialType(dto.socialType())
                .socialId(dto.socialId())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                // point, createdAt, updatedAt 은 엔티티의 @PrePersist 에서 처리됨
                .build();
    }
}
