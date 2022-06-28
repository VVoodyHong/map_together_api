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

    @Operation(summary = "search place")
    @PostMapping("/api/v1/app/place/search")
    public ResponseEntity<ApiResponse> searchPlace(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceDTO.Search search) {
        try {
            return ResponseMessageUtil.successMessage(placeService.searchPlace(userPrincipal.getIdx(), search));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "news place")
    @PostMapping("/api/v1/app/place/news")
    public ResponseEntity<ApiResponse> newsPlace(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody PlaceDTO.Search search) {
        try {
            return ResponseMessageUtil.successMessage(placeService.newsPlace(userPrincipal.getIdx(), search));
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "update place")
    @PutMapping("/api/v1/app/place")
    public ResponseEntity<ApiResponse> updatePlace(@RequestBody PlaceDTO.Update placeInfo) {
        try {
            return ResponseMessageUtil.successMessage(placeService.updatePlace(placeInfo));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "delete place")
    @DeleteMapping("/api/v1/app/place/{placeIdx}")
    public ResponseEntity<ApiResponse> deletePlace(@PathVariable Long placeIdx) {
        try {
            placeService.deletePlace(placeIdx);
            return ResponseMessageUtil.successMessage();
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

    @Operation(summary = "get place tags")
    @GetMapping("/api/v1/app/place/tag/{placeIdx}")
    public ResponseEntity<ApiResponse> getPlaceTag(@PathVariable Long placeIdx) {
        try {
            return ResponseMessageUtil.successMessage(placeService.getPlaceTag(placeIdx));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "create place like")
    @PostMapping("/api/v1/app/place/like/{placeIdx}")
    public ResponseEntity<ApiResponse> createPlaceLike(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long placeIdx) {
        try {
            placeService.createPlaceLike(userPrincipal, placeIdx);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "get place like")
    @GetMapping("/api/v1/app/place/like/{placeIdx}")
    public ResponseEntity<ApiResponse> getPlaceLike(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long placeIdx) {
        try {
            return ResponseMessageUtil.successMessage(placeService.getPlaceLike(userPrincipal, placeIdx));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "delete place like")
    @DeleteMapping("/api/v1/app/place/like/{placeIdx}")
    public ResponseEntity<ApiResponse> deletePlaceLike(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long placeIdx) {
        try {
            placeService.deletePlaceLike(userPrincipal, placeIdx);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}