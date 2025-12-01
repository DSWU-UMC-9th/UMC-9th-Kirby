package com.example.umc9th.domain.member.controller;

import com.example.umc9th.domain.member.dto.MemberReqDTO;
import com.example.umc9th.domain.member.dto.MemberResDTO;
import com.example.umc9th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc9th.domain.member.service.command.MemberCommandService;
import com.example.umc9th.domain.member.service.query.MemberQueryService; // [추가] 쿼리 서비스
import com.example.umc9th.domain.review.dto.ReviewResponse; // [추가]
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode; // [추가]
import com.example.umc9th.global.validation.annotation.CheckPage; // [추가] 검증 어노테이션
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated; // [추가]
import org.springframework.web.bind.annotation.*;

import com.example.umc9th.domain.mission.dto.MemberMissionResponse; // [추가]
import org.springframework.data.domain.Page; // [추가]

@RestController
@RequiredArgsConstructor
@Validated // [필수] 이 어노테이션이 있어야 @CheckPage가 동작합니다!
@RequestMapping("/members")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService; // [추가] 읽기 전용 서비스 주입

    // 1. 회원가입 (기존 코드 유지)
    @PostMapping("/sign-up")
    public ApiResponse<MemberResDTO.JoinDTO> signUp(
            @RequestBody MemberReqDTO.JoinDTO dto
    ) {
        MemberResDTO.JoinDTO response = memberCommandService.signUp(dto);

        return ApiResponse.onSuccess(
                MemberSuccessCode.MEMBER_CREATED,
                response
        );
    }

    // 2. [신규] 내가 쓴 리뷰 목록 조회 (페이징 + 검증)
    @GetMapping("/{memberId}/reviews")
    @Operation(summary = "내가 쓴 리뷰 목록 조회 API", description = "특정 회원의 리뷰 목록을 조회합니다. 페이징(Offset)을 포함합니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 아이디"),
            @Parameter(name = "page", description = "페이지 번호 (1번이 1페이지 입니다)")
    })
    public ApiResponse<ReviewResponse.ReviewPageDto> getMemberReviewList(
            @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page") Integer page // 커스텀 어노테이션 검증
    ) {
        // page - 1 : 프론트는 1페이지부터, DB는 0페이지부터 시작하므로 -1 처리
        ReviewResponse.ReviewPageDto result = memberQueryService.getMyReviewList(memberId, page - 1);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
    // 3. 내가 진행 중인 미션 목록 조회
    @GetMapping("/{memberId}/missions")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API", description = "내가 도전 중인 미션들을 조회합니다. (진행 중인 것만)")
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 아이디"),
            @Parameter(name = "page", description = "페이지 번호 (1번이 1페이지 입니다)")
    })
    public ApiResponse<Page<MemberMissionResponse>> getMemberMissionList(
            @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page") Integer page
    ) {
        Page<MemberMissionResponse> result = memberQueryService.getMyChallengingMissions(memberId, page - 1);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
    // 4. [신규] 진행 중인 미션 완료로 바꾸기
    @PatchMapping("/{memberId}/missions/{memberMissionId}")
    @Operation(summary = "미션 완료하기 API", description = "진행 중인 미션을 완료 상태로 변경합니다.")
    public ApiResponse<String> completeMission(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "memberMissionId") Long memberMissionId
    ) {
        memberCommandService.completeMission(memberId, memberMissionId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, "미션을 성공적으로 완료했습니다!");
    }
}
