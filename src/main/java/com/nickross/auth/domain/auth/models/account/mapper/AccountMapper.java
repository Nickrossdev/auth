package com.nickross.auth.domain.auth.models.account.mapper;

import com.nickross.auth.domain.auth.models.account.dto.AccountRegisterRequest;
import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.account.dto.AccountResponse;
import com.nickross.auth.domain.auth.models.account.dto.AccountUpdateRequest;
import com.nickross.auth.domain.auth.models.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nickross.auth.domain.auth.models.account.util.AccountUtil.getActiveRoleNames;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final PasswordEncoder passwordEncoder;

    public Account convertToEntity(AccountRegisterRequest request) {
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setActive(request.getActive() != null ? request.getActive() : true);
        return account;
    }
    public Account convertToEntity(AccountUpdateRequest request, Account account) {
        account.setEmail(request.getEmail() != null ? request.getEmail() : account.getEmail());
        account.setPassword(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : account.getPassword());
        account.setActive(request.getActive() != null ? request.getActive() : account.isActive());
        return account;
    }

    public AccountResponse convertToResponse(Account account) {
        return new AccountResponse(account.getId(),account.getEmail(),account.isActive(),getActiveRoleNames(account));
    }

    public List<AccountResponse> convertToResponse(List<Account> accountList) {
        return accountList.stream()
                .map(account -> {
                    return new AccountResponse(account.getId(), account.getEmail(), account.isActive(), getActiveRoleNames(account));
                })
                .toList();
    }
}
