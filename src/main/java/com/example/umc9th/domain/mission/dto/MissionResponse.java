package com.example.umc9th.domain.mission.dto;

import com.example.umc9th.domain.mission.entity.Mission;
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
public class MissionResponse {

    private Long missionId;
    private Integer point;
    private String conditional; // 미션 조건 (ex: 1만원 이상 구매)
    private LocalDate deadline;

    // Entity -> DTO 변환 메서드
    public static MissionResponse from(Mission mission) {
        return MissionResponse.builder()
                .missionId(mission.getId())
                .point(mission.getPoint())
                .conditional(mission.getConditional())
                .deadline(mission.getDeadline())
                .build();
    }

    // 페이징된 결과를 감싸는 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPageDto {
        private List<MissionResponse> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}