package com.spring.employee_management_system.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.employee_management_system.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

    boolean existsByEmail(String email);

    @Query("SELECT e FROM employees e ORDER BY e.firstName, e.lastName")
    List<Employee> findAllEmployeesSortedByName();

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM employees e WHERE e.email = :email AND e.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Integer id);

}
