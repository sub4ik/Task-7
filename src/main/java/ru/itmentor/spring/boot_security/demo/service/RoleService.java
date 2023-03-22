package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRoleByName(String role){
        return roleRepository.findByName(role);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Set<Role> getRolesByIds(List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role with id " + roleId + " not found"));
            roles.add(role);
        }
        return roles;
    }
}
