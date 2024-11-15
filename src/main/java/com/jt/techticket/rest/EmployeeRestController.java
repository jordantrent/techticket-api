package com.jt.techticket.rest;

import com.jt.techticket.dao.EmployeeRepository;
import com.jt.techticket.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable int id) {
        return employeeRepository.findById(id);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {

                    if (updates.containsKey("firstName")) {
                        existingEmployee.setFirstName((String) updates.get("firstName"));
                    }
                    if (updates.containsKey("lastName")) {
                        existingEmployee.setLastName((String) updates.get("lastName"));
                    }
                    if (updates.containsKey("position")) {
                        existingEmployee.setPosition((String) updates.get("position"));
                    }
                    if (updates.containsKey("phone")) {
                        existingEmployee.setPhone((String) updates.get("phone"));
                    }
                    if (updates.containsKey("email")) {
                        existingEmployee.setEmail((String) updates.get("email"));
                    }
                    if (updates.containsKey("photo")) {
                        existingEmployee.setPhoto((String) updates.get("photo"));
                    }


                    Employee updatedEmployee = employeeRepository.save(existingEmployee);
                    return ResponseEntity.ok(updatedEmployee);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee id not found - " + employeeId));

        employeeRepository.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }

}
