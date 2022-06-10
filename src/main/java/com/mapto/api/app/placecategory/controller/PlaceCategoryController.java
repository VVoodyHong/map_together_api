package com.mapto.api.app.placecategory.controller;

import com.mapto.api.app.placecategory.dto.PlaceCategoryDTO;
import com.mapto.api.app.placecategory.service.PlaceCategoryService;
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

@Tag(name = "place category", description = "place category API")
@RestController
@RequiredArgsConstructor
public class PlaceCategoryController {
    private final PlaceCategoryService placeCategoryService;

    @Operation(summary = "create place category")
    @PostMapping("/api/v1/app/place_category")
    public ResponseEntity<ApiResponse> createPlaceCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceCategoryDTO.Create placeCategory) {
        try {
            return ResponseMessageUtil.successMessage(placeCategoryService.createPlaceCategory(userPrincipal, placeCategory));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get place category list")
    @GetMapping("/api/v1/app/place_category")
    public ResponseEntity<ApiResponse> getPlaceCategory(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            return ResponseMessageUtil.successMessage(placeCategoryService.getPlaceCategory(userPrincipal));
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "delete place category list")
    @DeleteMapping("/api/v1/app/place_category")
    public ResponseEntity<ApiResponse> deletePlaceCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceCategoryDTO.Simples placeCategory) {
        try {
            placeCategoryService.deletePlaceCategory(userPrincipal, placeCategory);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}