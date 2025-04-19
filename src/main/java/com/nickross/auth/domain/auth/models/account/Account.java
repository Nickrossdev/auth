package com.nickross.auth.domain.auth.models.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean active;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<AccountRole> roles;
}
