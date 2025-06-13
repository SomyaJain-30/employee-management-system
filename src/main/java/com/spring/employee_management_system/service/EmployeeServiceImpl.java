package com.spring.employee_management_system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.spring.employee_management_system.entity.Employee;
import com.spring.employee_management_system.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAllEmployee() {
       return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public void addEmployee(Employee newEmployee) {
        employeeRepository.save(newEmployee);
        return;
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }

}
