package ru.ruslan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        System.out.println("LOGIN 1");
        return "login";
    }

    @PostMapping("/login")
    public String enterLogin() {
        System.out.println("LOGIN 2");
        return "test";
    }
}
