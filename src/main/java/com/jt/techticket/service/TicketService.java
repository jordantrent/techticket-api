package com.jt.techticket.service;

import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Ticket;
import com.jt.techticket.entity.Employee;

import java.util.List;
import java.util.Map;

public interface TicketService {

    public List<Ticket> findAll();
    public Ticket findById(int id);
    public void deleteById(int id);
    public Ticket addTicket(Ticket ticket);
    public Ticket assignEmployeesToTicket(int ticketId, List<Integer> employeeIds);
    public List<Employee> getEmployeesForTicket(int ticketId);
    public List<Ticket> getTicketsForEmployee(int employeeId);
    public Customer getCustomerForTicket(int ticketId);
    public Ticket updateTicket(int ticketId, Ticket ticket);
    public Map<String, Long> getTicketCounts();

}
