package com.example.umc9th.domain.member.service.query;

import com.example.umc9th.domain.review.dto.ReviewResponse;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.mission.dto.MemberMissionResponse; // [추가]
import com.example.umc9th.domain.mission.entity.MemberMission; // [추가]
import com.example.umc9th.domain.mission.repository.MemberMissionRepository; // [추가]
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
public class MemberQueryService {

    private final ReviewRepository reviewRepository;
    private final MemberMissionRepository memberMissionRepository; // 추가

    public ReviewResponse.ReviewPageDto getMyReviewList(Long memberId, Integer page) {

        // 1. 페이징 객체 생성 (page는 0부터 시작, 사이즈 10)
        PageRequest pageRequest = PageRequest.of(page, 10);

        // 2. 레포지토리 호출 (ReviewRepository에 이미 있는 메서드 사용)
        Page<Review> reviewPage = reviewRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageRequest);

        // 3. Entity -> DTO 변환
        List<ReviewResponse> reviewList = reviewPage.stream()
                .map(ReviewResponse::from) // 아까 ReviewResponse에 추가한 from 메서드 사용
                .collect(Collectors.toList());

        // 4. 응답 DTO 조립
        return new ReviewResponse.ReviewPageDto(
                null,
                reviewPage.hasNext(),
                (int) reviewPage.getTotalElements(),
                reviewList
        );
    }
    //  [신규 메서드] 내가 진행 중인 미션 목록
    public Page<MemberMissionResponse> getMyChallengingMissions(Long memberId, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10);

        // "진행 중"인 것만 가져오기 (IsCompleteFalse)
        Page<MemberMission> missionPage = memberMissionRepository.findByMemberIdAndIsCompleteFalse(memberId, pageRequest);

        // DTO 변환해서 반환
        return missionPage.map(MemberMissionResponse::from);
    }
}