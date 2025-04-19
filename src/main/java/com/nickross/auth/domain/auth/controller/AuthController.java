package com.nickross.auth.domain.auth.controller;

import com.nickross.auth.domain.auth.models.account.dto.AccountCurrentData;
import com.nickross.auth.domain.auth.models.account.dto.AccountLoginRequest;
import com.nickross.auth.domain.auth.models.account.dto.AccountRegisterRequest;
import com.nickross.auth.domain.auth.models.account.dto.AccountResponse;
import com.nickross.auth.domain.auth.service.AuthService;
import com.nickross.auth.dto.ApiResponse;
import com.nickross.auth.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountResponse,Void> > register(@RequestBody @Valid AccountRegisterRequest request) {
        AccountResponse response = authService.registerAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseUtil.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void, Void>> login(@RequestBody @Valid AccountLoginRequest request) {
        authService.loginAccount(request);
        return ResponseEntity.ok(ApiResponseUtil.success());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void, Void>> logout() {
        authService.logoutAccount();
        return ResponseEntity.ok(ApiResponseUtil.success());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AccountCurrentData,Void>> getCurrentAccountInfo(@AuthenticationPrincipal AccountCurrentData accountCurrentData) {
        return ResponseEntity.ok(ApiResponseUtil.success(accountCurrentData));
    }
}
