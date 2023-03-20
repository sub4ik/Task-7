package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.Collections;

/**
 * @author Neil Alishev
 */
@Service
public class RegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public RegistrationService(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleService.getRoleByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));
        userService.create(user);
    }
}
