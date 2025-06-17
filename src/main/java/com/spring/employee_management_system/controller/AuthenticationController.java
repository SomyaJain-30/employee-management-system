package com.spring.employee_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String showLogin(Model model){
        return "login";
    }

    @GetMapping("/userInfo")
    public String showUserInfo(Model model){
        return "user-info";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(Model model){
        return "access-denied";
    }
}
