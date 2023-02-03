package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Component
public class AdminController {

    private final UserServiceImpl userService;
    private Model model;

    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public String users(Model model) {
        List<User> user = userService.findAll();
        model.addAttribute("users", user);
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }
}
