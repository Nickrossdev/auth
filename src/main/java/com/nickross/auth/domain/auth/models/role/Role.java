package com.nickross.auth.domain.auth.models.role;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private boolean active;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AccountRole> accounts;
}
