package com.nickross.auth.domain.auth.models.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountUpdateRequest {
    private String email;
    private String password;
    private Boolean active;
    private List<Integer> roles;
}
