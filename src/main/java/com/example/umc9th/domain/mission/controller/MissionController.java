package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.dto.MissionResponse;
import com.example.umc9th.domain.mission.service.MissionCommandService;
import com.example.umc9th.domain.mission.service.query.MissionQueryService; // [추가]
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.validation.annotation.CheckPage; // [추가]
import com.example.umc9th.domain.store.validation.annotation.ExistStore; // [추가]
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated // [필수] 검증 어노테이션 활성화
@RequestMapping("/api/stores/{storeId}/missions")
public class MissionController {

    private final MissionCommandService missionCommandService;
    private final MissionQueryService missionQueryService; // [추가]

    // 1. [신규] 특정 가게의 미션 목록 조회
    @GetMapping
    @Operation(summary = "특정 가게의 미션 목록 조회 API", description = "특정 가게의 미션들을 조회합니다. 페이징(Offset)을 포함합니다.")
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호 (1번이 1페이지 입니다)")
    })
    public ApiResponse<MissionResponse.MissionPageDto> getMissionList(
            @ExistStore @PathVariable(name = "storeId") Long storeId, // 가게 존재 검증
            @CheckPage @RequestParam(name = "page") Integer page // 페이지 번호 검증
    ) {
        MissionResponse.MissionPageDto result = missionQueryService.getMissionList(storeId, page - 1);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    // 2. [기존] 미션 도전하기 (ApiResponse로 통일)
    @PostMapping("/{missionId}/challenge")
    @Operation(summary = "미션 도전하기 API", description = "특정 미션에 도전(참여)합니다.")
    public ApiResponse<Long> challengeMission(
            @ExistStore @PathVariable(name = "storeId") Long storeId,
            @PathVariable(name = "missionId") Long missionId
    ) {
        Long memberMissionId = missionCommandService.challengeMission(storeId, missionId);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, memberMissionId);
    }
}