package com.spring.employee_management_system.service;

import java.util.List;

import com.spring.employee_management_system.entity.Employee;

public interface EmployeeService {

    public List<Employee> findAllEmployee();

    public void addEmployee(Employee newEmployee);

    public boolean checkIfEmailExists(String email, Integer id);

    public List<Employee> getAllEmployeesSortedByName();

    public Employee findEmployeeById(int id);

    public void deleteEmployeeById(int id);
}
