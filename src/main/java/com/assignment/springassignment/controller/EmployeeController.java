package com.assignment.springassignment.controller;

import com.assignment.springassignment.Exception.EmployeeNotFound;
import com.assignment.springassignment.entities.Employee;
import com.assignment.springassignment.entities.EmployeeRepository;
import com.assignment.springassignment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/Employees")
public class EmployeeController {

     @Autowired
    public EmployeeRepository employeeService;
     @PostMapping(value = "/POST")
     public @ResponseBody Employee addEmployee(@Valid @RequestBody  Employee employee) throws SQLException {
         return employeeService.addemployee(employee);
     }
    @GetMapping(value = "/GET")
    public List<Employee> getAllemployees() throws SQLException {
        return employeeService.getAllemployees();
    }
    @GetMapping(value = "GET/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") int employeeId)  {
         Employee emp=employeeService.getemployeeid(employeeId);
         if (emp==null)
             throw new EmployeeNotFound("record not available");
        return emp;
    }
    @PutMapping("PUT/{employeeId}")
    public Employee updatemployee(@PathVariable("employeeId") int employeeId,@RequestBody Employee employee) throws SQLException {
        return employeeService.updateEmployee(employeeId,employee);
    }
    @DeleteMapping(value = "DELETE/{employeeId}")
    public void deleteemployee(@PathVariable("employeeId") int employeeId) throws SQLException {
        employeeService.deleteEmployee(employeeId);
    }
}
