package com.mapto.api.app.placereply.controller;

import com.mapto.api.app.placereply.dto.PlaceReplyDTO;
import com.mapto.api.app.placereply.service.PlaceReplyService;
import com.mapto.api.common.model.ApiResponse;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.common.model.RequestPage;
import com.mapto.api.common.util.ResponseMessageUtil;
import com.mapto.api.config.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "place reply", description = "place reply API")
@RestController
@RequiredArgsConstructor
public class PlaceReplyController {
    private final PlaceReplyService placeReplyService;

    @Operation(summary = "create place reply")
    @PostMapping("/api/v1/app/place_reply")
    public ResponseEntity<ApiResponse> createPlaceReply(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceReplyDTO.Create placeReply) {
        try {
            return ResponseMessageUtil.successMessage(placeReplyService.createPlaceReply(userPrincipal, placeReply));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get place reply")
    @GetMapping("/api/v1/app/place_reply/{placeIdx}")
    public ResponseEntity<ApiResponse> getPlaceReply(RequestPage requestPage, @PathVariable Long placeIdx) {
        try {
            return ResponseMessageUtil.successMessage(placeReplyService.getPlaceReply(requestPage, placeIdx));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get place reply")
    @DeleteMapping("/api/v1/app/place_reply/{placeReplyIdx}")
    public ResponseEntity<ApiResponse> deletePlaceReply(@PathVariable Long placeReplyIdx) {
        try {
            placeReplyService.deletePlaceReply(placeReplyIdx);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

}