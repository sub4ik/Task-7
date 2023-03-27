package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RegistrationService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RegistrationService registrationService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /*@GetMapping("")
    public String showUserList(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin/userList";
    }*/
    @GetMapping("")
    public String showUserList(Model model) {
        List<User> users = userService.getUsers();
        List<Role> roles = roleService.getAllRoles();
        User createUser = new User();
        model.addAttribute(createUser);
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "admin/adminPage";
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        User user = new User();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "admin/createUserForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, BindingResult result,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        if (result.hasErrors() || roleIds == null || roleIds.isEmpty()) {
            return "admin/createUserForm";
        }
        user.setRoles(roleService.getRolesByIds(roleIds));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.read(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/editUserForm";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        if (result.hasErrors() || roleIds == null || roleIds.isEmpty()) {
            return "admin/editUserForm";
        }
        user.setRoles(roleService.getRolesByIds(roleIds));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }
    /*@PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        if (result.hasErrors() || roleIds == null || roleIds.isEmpty()) {
            return "admin/editUserForm";
        }
        user.setRoles(roleService.getRolesByIds(roleIds));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }*/

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}

