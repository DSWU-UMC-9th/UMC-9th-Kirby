package com.example.umc9th.domain.member.service.command;

import com.example.umc9th.domain.food.entity.Food;
import com.example.umc9th.domain.food.exception.FoodException;
import com.example.umc9th.domain.food.exception.code.FoodErrorCode;
import com.example.umc9th.domain.food.repository.FoodRepository;
import com.example.umc9th.domain.member.converter.MemberConverter;
import com.example.umc9th.domain.member.dto.MemberReqDTO;
import com.example.umc9th.domain.member.dto.MemberResDTO;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.MemberFood;
import com.example.umc9th.domain.member.repository.MemberFoodRepository;
import com.example.umc9th.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.umc9th.domain.mission.entity.MemberMission; // [추가]
import com.example.umc9th.domain.mission.repository.MemberMissionRepository; // [추가]
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final FoodRepository foodRepository;
    private final MemberMissionRepository memberMissionRepository; // [추가] 주입 필요!

    // 회원가입
    @Override
    @Transactional
    public MemberResDTO.JoinDTO signUp(MemberReqDTO.JoinDTO dto) {

        // 사용자 생성 & 저장
        Member member = MemberConverter.toMember(dto);
        memberRepository.save(member);

        // 선호 음식 ID 리스트 꺼내기
        List<Long> preferFoodIds = dto.preferFoodIds();

        // 선호 음식 존재 여부 확인
        if (preferFoodIds != null && !preferFoodIds.isEmpty()) {

            List<MemberFood> memberFoodList = new ArrayList<>();

            for (Long id : preferFoodIds) {

                // 음식 존재 여부 검증
                Food food = foodRepository.findById(id)
                        .orElseThrow(() -> new FoodException(FoodErrorCode.NOT_FOUND));

                // MemberFood 엔티티 생성
                MemberFood memberFood = MemberFood.builder()
                        .member(member)
                        .food(food)
                        .build();

                memberFoodList.add(memberFood);
            }

            // 모든 선호 음식 저장
            memberFoodRepository.saveAll(memberFoodList);
        }

        // 응답 DTO 생성
        return MemberConverter.toJoinDTO(member);
    }
    // [추가] 미션 완료하기 구현
    @Override
    @Transactional
    public void completeMission(Long memberId, Long memberMissionId) {

        // 1. 해당 멤버미션 찾기
        MemberMission memberMission = memberMissionRepository.findById(memberMissionId)
                .orElseThrow(() -> new RuntimeException("MemberMission not found")); // 예외처리는 나중에 커스텀으로 변경 가능

        // 2. 주인이 맞는지 검증 (선택사항이지만 권장)
        if (!memberMission.getMember().getId().equals(memberId)) {
            throw new RuntimeException("This mission does not belong to the member");
        }

        // 3. 완료 상태로 변경 (Setter 대신 비즈니스 메서드 사용 권장)
        // MemberMission 엔티티에 setComplete 혹은 complete() 메서드가 필요합니다!
        // 일단은 Setter가 없으면 엔티티를 수정해야 합니다. 아래 설명 참고!

        // (임시) 만약 @Setter가 있다면:
        // memberMission.setComplete(true);

        // (권장) 엔티티에 메서드 만들기:
        memberMission.complete();
    }
}
