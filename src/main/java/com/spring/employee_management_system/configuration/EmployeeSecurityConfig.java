package com.spring.employee_management_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EmployeeSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails john = User.builder()
                                .username("john")
                                .password("{noop}test123")
                                .roles("EMPLOYEE")
                                .build();

        UserDetails mary = User.builder()
                                .username("mary")
                                .password("{noop}test123")
                                .roles("EMPLOYEE", "MANAGER")
                                .build();

        return new InMemoryUserDetailsManager(john, mary);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer->
            configurer
            .requestMatchers("/").hasRole("EMPLOYEE")
            .requestMatchers("/users/**").hasRole("MANAGER")
            .anyRequest().authenticated()
        )
        .formLogin(form -> 
            form.loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
        )
        .logout(logout->
            logout.permitAll()
        )
        .exceptionHandling(configurer->
            configurer.accessDeniedPage("/access-denied")
        );
        return http.build();
    }
}
