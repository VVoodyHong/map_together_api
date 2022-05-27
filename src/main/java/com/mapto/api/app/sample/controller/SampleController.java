package com.mapto.api.app.sample.controller;

import com.mapto.api.app.sample.service.SampleService;
import com.mapto.api.common.model.ApiResponse;
import com.mapto.api.common.model.RequestPage;
import com.mapto.api.common.util.ResponseMessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "sample", description = "sample API")
@RestController
@RequiredArgsConstructor
public class SampleController {

    private final SampleService service;

    @Operation(summary = "sample")
    @GetMapping("api/v1/app/sample")
    public ResponseEntity<ApiResponse> sample(Long idx) {
        try {
            return ResponseMessageUtil.successMessage(service.getSample(idx));
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "samples")
    @GetMapping("api/v1/app/samples")
    public ResponseEntity<ApiResponse> samples(RequestPage pageRequest, String keyword) {
        try {
            return ResponseMessageUtil.successMessage(service.getSamples(pageRequest, keyword));
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}
