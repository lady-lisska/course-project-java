package com.lisska.onboarding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Имя нашего HTML файла без расширения
    }
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }
}