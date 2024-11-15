package com.jt.techticket.service;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.dao.EmployeeRepository;
import com.jt.techticket.dao.TicketRepository;
import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Employee;
import com.jt.techticket.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final CustomerRepository customerRepository;
    TicketRepository ticketRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Ticket findById(int id) {
        return this.ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Did not find customer id - " + id));
    }

    @Override
    public void save(Ticket ticket) {
        this.ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(int id) {
        this.ticketRepository.deleteById(id);

    }

    @Override
    public Ticket assignEmployeesToTicket(int ticketId, List<Integer> employeeIds) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Did not find ticket id - " + ticketId));

        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        ticket.getEmployees().addAll(employees);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Employee> getEmployeesForTicket(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return new ArrayList<>(ticket.getEmployees());
    }

    @Override
    public List<Ticket> getTicketsForEmployee(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.getTickets();
    }

    @Override
    public Customer getCustomerForTicket(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticket.getCustomer();
    }
}
