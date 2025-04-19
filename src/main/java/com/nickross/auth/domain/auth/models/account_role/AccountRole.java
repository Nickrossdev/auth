package com.nickross.auth.domain.auth.models.account_role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

    private boolean active;
}
