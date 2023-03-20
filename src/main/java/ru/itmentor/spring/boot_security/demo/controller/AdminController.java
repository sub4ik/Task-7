package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin/userList";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        User user = userService.read(id);
        model.addAttribute("user", user);
        return "userDetail";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.read(id);
        model.addAttribute("user", user);
        return "userForm";
    }

    @PostMapping("/{id}/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/users/{id}";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        User user = userService.read(id);
        userService.delete(user);
        return "redirect:/users";
    }
}
