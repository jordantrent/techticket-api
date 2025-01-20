package com.jt.techticket.service;

import com.jt.techticket.dao.EmployeeRepository;
import com.jt.techticket.dao.TicketRepository;
import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Employee;
import com.jt.techticket.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, EmployeeRepository employeeRepository) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
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
    public void deleteById(int id) {
        this.ticketRepository.deleteById(id);
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket assignEmployeesToTicket(int ticketId, List<Integer> employeeIds) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Did not find ticket id - " + ticketId));
        List<Employee> employees = employeeRepository.findAllById(employeeIds);

        ticket.getEmployees().clear();
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

    //TODO: currently unsure if these update methods work 100% as intended, they are WIP.
    @Transactional
    public Ticket updateTicket(int ticketId, Ticket ticketDetails) {
        Ticket existingTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));

        if (ticketDetails.getIssueDescription() != null && !ticketDetails.getIssueDescription().isEmpty()) {
            existingTicket.setIssueDescription(ticketDetails.getIssueDescription());
        }
        if (ticketDetails.getStatus() != null && !ticketDetails.getStatus().isEmpty()) {
            existingTicket.setStatus(ticketDetails.getStatus());
        }
        if (ticketDetails.getCreatedDate() != null) {
            existingTicket.setCreatedDate(ticketDetails.getCreatedDate());
        }
        if (ticketDetails.getResolvedDate() != null) {
            existingTicket.setResolvedDate(ticketDetails.getResolvedDate());
        }
        if (ticketDetails.getImagePath() != null && !ticketDetails.getImagePath().isEmpty()) {
            existingTicket.setImagePath(ticketDetails.getImagePath());
        }

        return ticketRepository.save(existingTicket);
    }

    @Override
    public Map<String, Long> getTicketCounts() {
        long assigned = ticketRepository.countAssignedTickets();
        long unassigned = ticketRepository.countUnassignedTickets();

        Map<String, Long> response = new HashMap<>();
        response.put("assigned", assigned);
        response.put("unassigned", unassigned);

        return response;
    }

    @Override
    public List<Ticket> getInProgressTickets() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return allTickets.stream()
                .filter(ticket -> "In Progress".equals(ticket.getStatus()))
                .collect(Collectors.toList());
    }

}
