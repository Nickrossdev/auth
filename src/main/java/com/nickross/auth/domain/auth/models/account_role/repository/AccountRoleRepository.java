package com.nickross.auth.domain.auth.models.account_role.repository;

import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, UUID> {
    boolean existsByRoleId(Integer id);
    boolean existsByAccountId(UUID id);
}
