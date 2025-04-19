package com.nickross.auth.domain.auth.controller;

import com.nickross.auth.domain.auth.models.account.AccountService;
import com.nickross.auth.domain.auth.models.account.dto.AccountResponse;
import com.nickross.auth.dto.ApiResponse;
import com.nickross.auth.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>,Void>> getAllAccounts() {
        List<AccountResponse> data = accountService.getAllAccounts();
        return ResponseEntity.ok(ApiResponseUtil.success("Cuentas obtenidas",data));
    }

}
