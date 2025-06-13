package com.spring.employee_management_system.controller;

import java.util.List;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.employee_management_system.entity.Employee;
import com.spring.employee_management_system.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    //Mappings
    @GetMapping("/list")
    public String listEmployees(Model model){

        //get data from db
        List<Employee> theEmployees = employeeService.findAllEmployee();

        //adding data to the model to show in the UI
        model.addAttribute("employees", theEmployees);

        return "list-employees";

    }


    @GetMapping("/add")
    public String addEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/save")
    public String saveNewEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult){
        
        if(employeeService.checkIfEmailExists(employee.getEmail())){
            bindingResult.rejectValue("email", "duplicate.email",  "Email already exists");
        }

        //checking if there is any validation error or not
        if(bindingResult.hasErrors()){
            return "add-employee";
        }

        //otherwise add employee to the database
        employeeService.addEmployee(employee);

        //again redirect to the employee list
        return "redirect:/employees/list";
    }


}
