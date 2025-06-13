package com.spring.employee_management_system.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.employee_management_system.service.EmployeeService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String>{

    private final EmployeeService employeeService;

    @Autowired
    public UniqueEmailConstraintValidator(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(String userEmail, ConstraintValidatorContext constraintValidatorContext) {
        
        if(userEmail == null) return true;
        return !employeeService.checkIfEmailExists(userEmail);
    }

}
