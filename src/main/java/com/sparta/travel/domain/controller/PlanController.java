package com.sparta.travel.domain.controller;

import com.sparta.travel.domain.dto.MsgResponseDto;
import com.sparta.travel.domain.dto.PlanRequestDto;
import com.sparta.travel.domain.dto.PlanResponseDto;
import com.sparta.travel.domain.security.UserDetailsImpl;
import com.sparta.travel.domain.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping("/schedule")
    public MsgResponseDto createPlan(@RequestBody PlanRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return planService.createPlan(requestDto, userDetails.getUser());
    }

    @GetMapping("/mytravel/{userId}")
    public List<PlanResponseDto> getPlan(@PathVariable String userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return planService.getPlan(userId, userDetails.getUser());
    }

    @PutMapping("/schedule/{plan_id}")
    public MsgResponseDto updatePlan(@PathVariable Long plan_id, @RequestBody PlanRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return planService.updatePlan(plan_id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/schedule/{plan_id}")
    public MsgResponseDto deletePlan(@PathVariable Long plan_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return planService.deletePlan(plan_id, userDetails.getUser());
    }
}

