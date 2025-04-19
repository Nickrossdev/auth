package com.nickross.auth.seeder;

import com.nickross.auth.domain.auth.models.account.Account;
import com.nickross.auth.domain.auth.models.account_role.AccountRole;
import com.nickross.auth.domain.auth.models.role.Role;
import com.nickross.auth.domain.auth.models.account_role.AccountRoleService;
import com.nickross.auth.domain.auth.models.account.AccountService;
import com.nickross.auth.domain.auth.models.role.RoleService;
import com.nickross.auth.domain.auth.models.role.dto.RoleRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountAdminSeeder implements CommandLineRunner {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountRoleService accountRoleService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("ADMIN", "Administrador del sistema");
        createRoleIfNotExists("USER", "Usuario normal");

        String adminEmail = "admin@correo.com";
        String password = "1234";

        if (!accountService.accountEmailExist(adminEmail)) {
            Account admin = new Account();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setActive(true);

            Account account = accountService.saveAccount(admin);
            Role role = roleService.findEntityByName("ADMIN");
            AccountRole accountRole = new AccountRole();
            accountRole.setAccount(account);
            accountRole.setRole(role);
            accountRole.setActive(true);
            accountRoleService.createAccountRole(accountRole);

            System.out.println("Cuenta admin creada: " + adminEmail);
        } else {
            System.out.println("Cuenta admin ya existe: " + adminEmail);
        }
    }

    private void createRoleIfNotExists(String name, String description) {
        if (!roleService.roleNameExist(name)) {
            RoleRegisterRequest request = new RoleRegisterRequest();
            request.setName(name);
            request.setDescription(description);
            roleService.createRole(request);
            System.out.println("Rol creado: " + name);
        }
    }
}
