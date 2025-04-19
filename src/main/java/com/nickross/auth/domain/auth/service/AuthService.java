package com.nickross.auth.domain.auth.service;

import com.nickross.auth.domain.auth.models.account.AccountService;
import com.nickross.auth.domain.auth.models.account.dto.AccountLoginRequest;
import com.nickross.auth.domain.auth.models.account.dto.AccountRegisterRequest;
import com.nickross.auth.domain.auth.models.account.dto.AccountResponse;
import com.nickross.auth.domain.auth.models.account.exceptions.InvalidPasswordException;
import com.nickross.auth.domain.auth.models.account.mapper.AccountMapper;
import com.nickross.auth.domain.auth.models.account_role.AccountRoleService;
import com.nickross.auth.domain.auth.models.account_role.mapper.AccountRoleMapper;
import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import com.nickross.auth.domain.auth.models.role.Role;
import com.nickross.auth.domain.auth.models.role.RoleService;
import com.nickross.auth.security.CookieService;
import com.nickross.auth.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountRoleService accountRoleService;
    private final AccountMapper accountMapper;
    private final AccountRoleMapper accountRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CookieService cookieService;

    public AccountResponse registerAccount(@Valid AccountRegisterRequest request) {
        Account account = accountService.createAccountEntity(request);


        Role role = roleService.findEntityByName("USER");
        AccountRole accountRole = accountRoleMapper.convertToEntity(account, role);
        accountRole = accountRoleService.createAccountRole(accountRole);

        account.setRoles(Set.of(accountRole));
        return accountMapper.convertToResponse(account);
    }

    public void loginAccount(@Valid AccountLoginRequest request) {
        Account account = accountService.getAccountByEmail(request.getEmail());
        boolean isMatch = passwordEncoder.matches(request.getPassword(),account.getPassword());
        if (!isMatch) {
            throw new InvalidPasswordException("Contraseña incorrecta");
        }
        String token = jwtUtil.generateToken(account.getId());
        cookieService.createCookie(token);
    }

    public void logoutAccount() {
        cookieService.createCookie();
    }
}
