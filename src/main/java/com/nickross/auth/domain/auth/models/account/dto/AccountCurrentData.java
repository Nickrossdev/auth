package com.nickross.auth.domain.auth.models.account.dto;

import com.nickross.auth.domain.auth.models.account.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import static com.nickross.auth.domain.auth.models.account.util.AccountUtil.getActiveRoleNames;

@Getter
@Setter
public class AccountCurrentData {
    private UUID id;
    private String email;
    private List<String> roles;

    public AccountCurrentData(Account account){
        this.id = account.getId();
        this.email = account.getEmail();
        this.roles = getActiveRoleNames(account);
    }
}
