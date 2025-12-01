package com.example.umc9th.domain.member.service.command;

import com.example.umc9th.domain.member.dto.MemberReqDTO;
import com.example.umc9th.domain.member.dto.MemberResDTO;

public interface MemberCommandService {

    // 회원가입
    MemberResDTO.JoinDTO signUp(MemberReqDTO.JoinDTO dto);

    // [추가] 미션 완료하기
    void completeMission(Long memberId, Long memberMissionId);
}
