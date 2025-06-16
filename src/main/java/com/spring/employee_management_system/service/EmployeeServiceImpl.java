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
    public boolean checkIfEmailExists(String email, Integer id) {
        return employeeRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public List<Employee> getAllEmployeesSortedByName() {
        return employeeRepository.findAllEmployeesSortedByName();
    }

    @Override
    public Employee findEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(new Employee());
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }

}
