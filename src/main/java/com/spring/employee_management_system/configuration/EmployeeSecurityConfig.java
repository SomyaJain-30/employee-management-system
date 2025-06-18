package com.spring.employee_management_system.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.employee_management_system.service.UserService;

@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception{ 
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
            authBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

            
            return authBuilder.build();
    }

    // @Bean
    // public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService){
    //     DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    //     auth.setUserDetailsService(userService);
    //     auth.setPasswordEncoder(passwordEncoder());

    //     return auth;
    // }
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer->
            configurer
            .requestMatchers("/").hasRole("EMPLOYEE")
            .requestMatchers("/users/**").hasRole("MANAGER")
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> 
            form.loginPage("/auth/login")
                .loginProcessingUrl("/auth/authenticateTheUser")
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

    //user detail manager with custom tables
    // @Bean
    // public UserDetailsManager userDetailsManager(DataSource dataSource){
    //     JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

    //     userDetailsManager.setUsersByUsernameQuery("select email, pw, isActive from members where email=?");

    //     userDetailsManager.setAuthoritiesByUsernameQuery("select email, role from roles where email=?");

    //     return userDetailsManager;
    // }

    //Jdbc user detail manager, here we are using default tables and columns 
    // @Bean
    // public UserDetailsManager userDetailsManager(DataSource dataSource){
    //     return new JdbcUserDetailsManager(dataSource);
    // }


    //In Memory user detail manager, here hardcoded data is there
    // @Bean
    // public InMemoryUserDetailsManager userDetailsManager(){

    //     UserDetails john = User.builder()
    //                             .username("john")
    //                             .password("{noop}test123")
    //                             .roles("EMPLOYEE")
    //                             .build();

    //     UserDetails mary = User.builder()
    //                             .username("mary")
    //                             .password("{noop}test123")
    //                             .roles("EMPLOYEE", "MANAGER")
    //                             .build();

    //     return new InMemoryUserDetailsManager(john, mary);
    // }
}
