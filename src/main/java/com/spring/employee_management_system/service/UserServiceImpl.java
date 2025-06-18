package com.spring.employee_management_system.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.employee_management_system.entity.User;
import com.spring.employee_management_system.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Invalid email");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthrities(user));

    }

    private Collection<? extends GrantedAuthority> getAuthrities(User user){
        return user.getRoles().stream()
        .map(role-> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
    }

}
