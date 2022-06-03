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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "place category", description = "place category API")
@RestController
@RequiredArgsConstructor
public class PlaceCategoryController {
    private final PlaceCategoryService placeCategoryService;

    @Operation(summary = "create place category")
    @PostMapping("/api/v1/app/place_category")
    public ResponseEntity<ApiResponse> createPlaceCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceCategoryDTO.Create placeCategoryInfo) {
        try {
            return ResponseMessageUtil.successMessage(placeCategoryService.createPlaceCategory(userPrincipal, placeCategoryInfo));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get place category list")
    @GetMapping("/api/v1/app/place_categories")
    public ResponseEntity<ApiResponse> getPlaceCategories(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            return ResponseMessageUtil.successMessage(placeCategoryService.getPlaceCategories(userPrincipal));
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}