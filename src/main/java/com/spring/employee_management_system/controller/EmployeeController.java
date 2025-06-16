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
import org.springframework.web.bind.annotation.RequestParam;

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

    //Get List of Employees
    @GetMapping("/list")
    public String listEmployees(Model model){
        //get data from db
        // List<Employee> theEmployees = employeeService.findAllEmployee();
        List<Employee> theEmployees = employeeService.getAllEmployeesSortedByName();
        //adding data to the model to show in the UI
        model.addAttribute("employees", theEmployees);

        return "list-employees";

    }

    //Add New Employee
    @GetMapping("/add")
    public String addEmployee(Model model){
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/save")
    public String saveNewEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult){
        
         //checking if there is any validation error or not
        if(bindingResult.hasErrors()){
            return "add-employee";
        }

        if(employeeService.checkIfEmailExists(employee.getEmail(), employee.getId())){
            bindingResult.rejectValue("email", "duplicate.email",  "Email already exists");
            return "add-employee";
        }
       
        //otherwise add employee to the database
        employeeService.addEmployee(employee);

        //again redirect to the employee list
        return "redirect:/employees/list";
    }

    //Update existing employee
    @GetMapping("/update")
    public String updateEmployee(@RequestParam("employeeId") int id, Model model){
        Employee employee = employeeService.findEmployeeById(id);
        System.out.println("update id: " + employee.getId());
        model.addAttribute("employee", employee);
        return "add-employee";
    }

    //delete employee

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees/list";
    }
}
