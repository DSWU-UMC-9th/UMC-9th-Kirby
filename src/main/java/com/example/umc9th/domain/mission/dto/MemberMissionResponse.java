package com.example.umc9th.domain.mission.dto;

import com.example.umc9th.domain.mission.entity.MemberMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMissionResponse {

    private Long memberMissionId; // 나중에 "완료하기" 버튼 누를 때 필요함
    private String storeName;
    private String missionSpec;   // 미션 내용 (conditional)
    private Integer point;
    private LocalDate deadline;
    private boolean isComplete;

    // Entity -> DTO 변환
    public static MemberMissionResponse from(MemberMission memberMission) {
        return MemberMissionResponse.builder()
                .memberMissionId(memberMission.getId())
                .storeName(memberMission.getMission().getStore().getName())
                .missionSpec(memberMission.getMission().getConditional())
                .point(memberMission.getMission().getPoint())
                .deadline(memberMission.getMission().getDeadline())
                .isComplete(memberMission.isComplete())
                .build();
    }
}