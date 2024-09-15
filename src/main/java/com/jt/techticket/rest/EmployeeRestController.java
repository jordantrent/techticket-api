package com.jt.techticket.rest;

import com.jt.techticket.dao.EmployeeRepository;
import com.jt.techticket.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRestController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee id not found - " + employeeId));

        employeeRepository.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }

}
