package com.mapto.api.app.place.controller;

import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.app.place.service.PlaceService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "place", description = "place API")
@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "create place")
    @PostMapping("/api/v1/app/place")
    public ResponseEntity<ApiResponse> createPlace(@AuthenticationPrincipal UserPrincipal userPrincipal, PlaceDTO.Create placeInfo, @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        try {
            return ResponseMessageUtil.successMessage(placeService.createPlace(userPrincipal, placeInfo, files));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get place images")
    @GetMapping("/api/v1/app/place/image/{placeIdx}")
    public ResponseEntity<ApiResponse> getPlaceImage(@PathVariable Long placeIdx) {
        try {
            return ResponseMessageUtil.successMessage(placeService.getPlaceImage(placeIdx));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}