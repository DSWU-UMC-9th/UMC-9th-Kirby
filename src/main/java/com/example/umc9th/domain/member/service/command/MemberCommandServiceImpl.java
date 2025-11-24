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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final FoodRepository foodRepository;

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
}
