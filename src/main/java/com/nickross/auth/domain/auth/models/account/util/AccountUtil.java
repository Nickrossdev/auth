package com.nickross.auth.domain.auth.models.account.util;

import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import com.nickross.auth.domain.auth.models.role.Role;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountUtil {
    public static List<String> getActiveRoleNames(Account account) {
        return account.getRoles().stream()
                .filter(AccountRole::isActive)              // solo roles activos
                .map(AccountRole::getRole)
                .filter(Objects::nonNull)
                .filter(Role::isActive)                     // también el rol en sí debe estar activo
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
