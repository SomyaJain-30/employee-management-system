package com.spring.employee_management_system.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.spring.employee_management_system.entity.User;

public interface UserService extends UserDetailsService{

    public void saveUser(User user);

    public User findByEmail(String email);
}
