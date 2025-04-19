package com.nickross.auth.domain.auth.models.account_role.mapper;

import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import com.nickross.auth.domain.auth.models.role.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountRoleMapper {

    public AccountRole convertToEntity(Account account, Role role) {
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(account);
        accountRole.setRole(role);
        accountRole.setActive(true);
        return accountRole;
    }

    public List<AccountRole> convertToEntity(Account account, List<Role> roleList) {
        return roleList.stream()
                .map(role -> {
                    AccountRole accountRole = new AccountRole();
                    accountRole.setAccount(account);
                    accountRole.setRole(role);
                    accountRole.setActive(true);
                    return accountRole;
                }).toList();
    }
}
