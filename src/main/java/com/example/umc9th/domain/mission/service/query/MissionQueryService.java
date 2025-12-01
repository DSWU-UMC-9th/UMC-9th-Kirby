package com.example.umc9th.domain.mission.service.query;

import com.example.umc9th.domain.mission.dto.MissionResponse;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryService {

    private final MissionRepository missionRepository;

    public MissionResponse.MissionPageDto getMissionList(Long storeId, Integer page) {

        // 1. 페이징 객체 생성 (page는 0부터 시작, 10개씩)
        PageRequest pageRequest = PageRequest.of(page, 10);

        // 2. DB 조회 (가게 ID로 미션 찾기)
        Page<Mission> missionPage = missionRepository.findAllByStoreIdOrderByDeadlineAsc(storeId, pageRequest);

        // 3. Entity -> DTO 변환
        List<MissionResponse> missionList = missionPage.stream()
                .map(MissionResponse::from)
                .collect(Collectors.toList());

        // 4. 응답 DTO 조립
        return MissionResponse.MissionPageDto.builder()
                .missionList(missionList)
                .listSize(missionList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }
}