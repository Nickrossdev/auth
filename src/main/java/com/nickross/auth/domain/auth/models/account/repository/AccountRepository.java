package com.nickross.auth.domain.auth.models.account.repository;

import com.nickross.auth.domain.auth.models.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findById(UUID id);

    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles.role"})
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdIncludingRoles(@Param("id") UUID id);
}
