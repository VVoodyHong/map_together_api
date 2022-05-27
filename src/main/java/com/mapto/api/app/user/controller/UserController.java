package com.mapto.api.app.user.controller;

import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.common.model.ApiResponse;
import com.mapto.api.common.model.type.ExistType;
import com.mapto.api.common.util.ResponseMessageUtil;
import com.mapto.api.app.user.service.UserService;
import com.mapto.api.common.model.CustomException;
import com.mapto.api.config.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "user API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "sign up")
    @PostMapping("/api/v1/app/user/signUp")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserDTO.Create userInfo) {
        try {
            userService.createUser(userInfo);
            return ResponseMessageUtil.successMessage();
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "user exist check")
    @GetMapping("/api/v1/app/user/exist")
    public ResponseEntity<ApiResponse> existUser(String value, ExistType type) {
        try {
            return ResponseMessageUtil.successMessage(userService.checkExistUser(value, type));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "user info")
    @GetMapping("/api/v1/app/user")
    public ResponseEntity<ApiResponse> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            return ResponseMessageUtil.successMessage(userService.readUser(userPrincipal));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }

    @Operation(summary = "user update")
    @PatchMapping("/api/v1/app/user/{idx}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long idx, @RequestBody UserDTO.Update userInfo) {
        try {
            return ResponseMessageUtil.successMessage(userService.updateUser(idx, userInfo));
        } catch(CustomException ce) {
            return ResponseMessageUtil.errorMessage(ce.getCode());
        } catch(Exception e) {
            return ResponseMessageUtil.errorMessage(e);
        }
    }
}
