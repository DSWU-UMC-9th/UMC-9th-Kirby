package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 4-A) 선택 지역 + 마감 전 미션 (페이징)
    Page<Mission> findByStoreLocationIdAndDeadlineAfter(Long locationId, LocalDate today, Pageable pageable);

    // 4-B) '도전 가능' = 아직 이 회원이 참여(기록)하지 않은 미션 + 마감 전 (페이징)
    @Query("""
        select m
        from Mission m
        where m.store.location.id = :locationId
          and m.deadline > :today
          and not exists (
                select 1
                from MemberMission mm
                where mm.mission = m
                  and mm.member.id = :memberId
          )
        """)
    Page<Mission> findAvailableMissions(Long locationId, Long memberId, LocalDate today, Pageable pageable);
}
