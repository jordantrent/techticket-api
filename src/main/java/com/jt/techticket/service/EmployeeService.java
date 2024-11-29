package com.jt.techticket.service;

import com.jt.techticket.entity.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> findAll();
    public Employee findById(int id);
    public Employee addEmployee(Employee employee);
    public void deleteById(int id);
    public void unassignTickets(int employeeId);
    public Employee updateEmployee(int employeeId, Employee employee);

}
