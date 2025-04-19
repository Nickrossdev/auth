package com.nickross.auth.domain.auth.models.account_role;

import com.nickross.auth.domain.auth.models.account_role.repository.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;

    public AccountRole createAccountRole(AccountRole accountRole) {
        return accountRoleRepository.save(accountRole);
    }
    public List<AccountRole> createAccountRole(List<AccountRole> accountRoleList) {
        return accountRoleRepository.saveAll(accountRoleList);
    }

    public boolean existsAccountRoleByRoleId(Integer id) {
        return accountRoleRepository.existsByRoleId(id);
    }

    public boolean existsAccountRoleByAccountId(UUID id) {
        return accountRoleRepository.existsByAccountId(id);
    }
}
