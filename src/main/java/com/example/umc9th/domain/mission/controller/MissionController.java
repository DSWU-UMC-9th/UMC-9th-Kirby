package com.example.umc9th.domain.mission.controller;

import com.example.umc9th.domain.mission.service.MissionCommandService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores/{storeId}/missions")
public class MissionController {

    private final MissionCommandService missionCommandService;

    public MissionController(MissionCommandService missionCommandService) {
        this.missionCommandService = missionCommandService;
    }


    @PostMapping("/{missionId}/challenge")
    public ResponseEntity<ApiResponse<Long>> challengeMission(
            @PathVariable Long storeId,
            @PathVariable Long missionId
    ) {
        Long memberMissionId = missionCommandService.challengeMission(storeId, missionId);

        return ResponseEntity.ok(
                ApiResponse.onSuccess(GeneralSuccessCode.OK, memberMissionId)
        );
    }
}
