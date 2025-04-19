package com.nickross.auth.domain.auth.models.account;

import com.nickross.auth.domain.auth.models.account.dto.AccountRegisterRequest;
import com.nickross.auth.domain.auth.models.account.dto.AccountResponse;
import com.nickross.auth.domain.auth.models.account.dto.AccountUpdateRequest;
import com.nickross.auth.domain.auth.models.account.exceptions.*;
import com.nickross.auth.domain.auth.models.account.mapper.AccountMapper;
import com.nickross.auth.domain.auth.models.account.repository.AccountRepository;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import com.nickross.auth.domain.auth.models.account_role.AccountRoleService;
import com.nickross.auth.domain.auth.models.account_role.mapper.AccountRoleMapper;
import com.nickross.auth.domain.auth.models.role.Role;
import com.nickross.auth.domain.auth.models.role.RoleService;
import com.nickross.auth.domain.auth.models.role.exceptions.RoleNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final RoleService roleService;
    private final AccountRoleService accountRoleService;
    private final AccountRoleMapper accountRoleMapper;

    public Account findEntityById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("La cuenta no fue encontrada con el id"));
    }
    public Account findEntityByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RoleNotFoundException("la cuenta no fue encontrada con el email"));
    }
    public Account findAccountAllRoles(UUID accountId) {
        return accountRepository.findByIdIncludingRoles(accountId)
                .orElseThrow(() -> new AccountNotFoundException("No se encontró una cuenta con el id"));
    }

    public List<AccountResponse> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountMapper.convertToResponse(accountList);
    }

    public AccountResponse getAccountById(UUID id) {
        Account account = findEntityById(id);
        return accountMapper.convertToResponse(account);
    }

    public Account saveAccount(Account account) {
        try {
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new AccountSaveException("No se pudo guardar la cuenta: violación de integridad de datos", e);
        } catch (ConstraintViolationException e) {
            throw new AccountValidationException("Error de validación en los campos de la cuenta", e);
        } catch (Exception e) {
            throw new GeneralAccountException("Error inesperado al guardar la cuenta", e);
        }
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(()-> new AccountNotFoundException("No se encontró una cuenta asociada al email"));
    }

    public boolean accountEmailExist(String email) {
        return accountRepository.existsByEmail(email);
    }

    public void validateUniqueAccountEmail(String email) {
        if(accountEmailExist(email)) {
            throw new EmailAlreadyUsedException("Ese email ya está en uso");
        }
    }

    public AccountResponse createAccount(AccountRegisterRequest request) {
        Account account = accountMapper.convertToEntity(request);
        validateUniqueAccountEmail(account.getEmail());
        account = accountRepository.save(account);

        List<Integer> rolesId = request.getRoles();
        if (rolesId != null && !rolesId.isEmpty()) {
            List<Role> roles = roleService.findAllEntityById(rolesId);
            Set<AccountRole> accountRoleSet = new HashSet<>(accountRoleMapper.convertToEntity(account, roles));
            List<AccountRole> accountRoleList = new ArrayList<>(accountRoleSet);
            accountRoleService.createAccountRole(accountRoleList);
            account.setRoles(accountRoleSet);
        }
        account = accountRepository.save(account);
        return accountMapper.convertToResponse(account);
    }

    public Account createAccountEntity(AccountRegisterRequest request) {
        Account account = accountMapper.convertToEntity(request);
        validateUniqueAccountEmail(account.getEmail());
        return accountRepository.save(account);
    }


    public AccountResponse updateAccount(UUID id, AccountUpdateRequest request) {
        Account account = findEntityById(id);
        account = accountMapper.convertToEntity(request, account);

        if (!account.getEmail().equals(request.getEmail())) {
            validateUniqueAccountEmail(account.getEmail());
        }

        account = accountRepository.save(account);
        return accountMapper.convertToResponse(account);
    }

    public void updateAccountActiveStatus(UUID id, boolean active) {
        Account account = findEntityById(id);
        if (account.isActive() == active) {
            throw new RedundantAccountStateException("La cuenta ya está marcado como: " + (active ? "activo" : "inactivo"));
        }
        account.setActive(active);
        saveAccount(account);
    }

    public void deleteAccount(UUID id) {
        boolean exists = accountRoleService.existsAccountRoleByAccountId(id);
        if(exists) {
            throw new AccountInUseException("La cuenta esta asignado a uno o mas roles");
        }
        Account account = findEntityById(id);
        accountRepository.delete(account);
    }
}
