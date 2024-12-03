package com.jt.techticket.service;

import com.jt.techticket.dao.EmployeeRepository;
import com.jt.techticket.dao.TicketRepository;
import com.jt.techticket.entity.Employee;
import com.jt.techticket.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, TicketRepository ticketRepository) {
        this.employeeRepository = employeeRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Did not find employee id - " + id));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void unassignTickets(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Ticket> tickets = ticketRepository.findByEmployees_Id(employeeId);

        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets found for this employee"); //TODO: improve exception handling
        }

        tickets.forEach(ticket -> ticket.getEmployees().remove(employee));

        ticketRepository.saveAll(tickets);
        employeeRepository.save(employee);
    }

    //TODO: currently unsure if these update methods work 100% as intended, they are WIP.
    @Transactional
    public Employee updateEmployee(int employeeId, Employee employeeDetails) {

        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        if (employeeDetails.getFirstName() != null && !employeeDetails.getFirstName().isEmpty()) {
            existingEmployee.setFirstName(employeeDetails.getFirstName());
        }
        if (employeeDetails.getLastName() != null && !employeeDetails.getLastName().isEmpty()) {
            existingEmployee.setLastName(employeeDetails.getLastName());
        }
        if (employeeDetails.getPosition() != null && !employeeDetails.getPosition().isEmpty()) {
            existingEmployee.setPosition(employeeDetails.getPosition());
        }
        if (employeeDetails.getPhone() != null && !employeeDetails.getPhone().isEmpty()) {
            existingEmployee.setPhone(employeeDetails.getPhone());
        }
        if (employeeDetails.getEmail() != null && !employeeDetails.getEmail().isEmpty()) {
            existingEmployee.setEmail(employeeDetails.getEmail());
        }
        if (employeeDetails.getPhoto() != null && !employeeDetails.getPhoto().isEmpty()) {
            existingEmployee.setPhoto(employeeDetails.getPhoto());
        }

        return employeeRepository.save(existingEmployee);
    }
}
