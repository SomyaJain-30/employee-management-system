package com.spring.employee_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.employee_management_system.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findByName(String name);
}
