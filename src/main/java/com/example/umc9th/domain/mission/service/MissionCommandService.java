package com.example.umc9th.domain.mission.service;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.mission.entity.MemberMission;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.repository.MemberMissionRepository;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MissionCommandService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public MissionCommandService(
            MissionRepository missionRepository,
            MemberMissionRepository memberMissionRepository,
            StoreRepository storeRepository,
            MemberRepository memberRepository
    ) {
        this.missionRepository = missionRepository;
        this.memberMissionRepository = memberMissionRepository;
        this.storeRepository = storeRepository;
        this.memberRepository = memberRepository;
    }

    public Long challengeMission(Long storeId, Long missionId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        if (!mission.getStore().getId().equals(store.getId())) {
            throw new RuntimeException("Mission does not belong to this store");
        }

        Member member = memberRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MemberMission memberMission = MemberMission.builder()
                .member(member)
                .mission(mission)
                .build();

        memberMissionRepository.save(memberMission);
        return memberMission.getId();
    }
}
