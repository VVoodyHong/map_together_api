package com.mapto.api.app.follow.controller;

import com.mapto.api.app.follow.dto.FollowDTO;
import com.mapto.api.app.follow.service.FollowService;
import com.mapto.api.common.model.ApiResponse;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.util.ResponseMessageUtil;
import com.mapto.api.config.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "follow", description = "follow API")
@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "create follow")
    @PostMapping("/api/v1/app/follow/{toUserIdx}")
    public ResponseEntity<ApiResponse> createFollow(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long toUserIdx) {
        try {
            followService.createFollow(userPrincipal.getIdx(), toUserIdx);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "search follow")
    @PostMapping("/api/v1/app/follow/search")
    public ResponseEntity<ApiResponse> searchFollow(@RequestBody FollowDTO.Search search) {
        try {
            return ResponseMessageUtil.successMessage(followService.searchFollow(search));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get follow count")
    @GetMapping("/api/v1/app/follow/count/{userIdx}")
    public ResponseEntity<ApiResponse> getFollowCount(@PathVariable Long userIdx) {
        try {
            return ResponseMessageUtil.successMessage(followService.getFollowCount(userIdx));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get follow state")
    @GetMapping("/api/v1/app/follow/state/{userIdx}")
    public ResponseEntity<ApiResponse> getFollowState(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userIdx) {
        try {
            return ResponseMessageUtil.successMessage(followService.getFollowState(userPrincipal.getIdx(), userIdx));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "delete follow")
    @DeleteMapping("/api/v1/app/follow/{toUserIdx}")
    public ResponseEntity<ApiResponse> deleteFollow(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long toUserIdx) {
        try {
            followService.deleteFollow(userPrincipal.getIdx(), toUserIdx);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}
