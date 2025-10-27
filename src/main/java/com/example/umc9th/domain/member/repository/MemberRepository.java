package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 필요 시 파생쿼리 추가 가능 (예: 이메일로 조회 등)
    // Optional<Member> findByEmail(String email);
}
