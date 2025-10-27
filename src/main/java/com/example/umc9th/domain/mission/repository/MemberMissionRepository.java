package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 진행중 (is_complete = false)
    Page<MemberMission> findByMemberIdAndIsCompleteFalse(Long memberId, Pageable pageable);

    // 진행완료 (is_complete = true)
    Page<MemberMission> findByMemberIdAndIsCompleteTrue(Long memberId, Pageable pageable);
}
