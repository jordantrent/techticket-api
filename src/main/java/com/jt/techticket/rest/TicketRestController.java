package com.jt.techticket.rest;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.dao.TicketRepository;
import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Employee;
import com.jt.techticket.entity.Ticket;
import com.jt.techticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TicketRestController {

    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public TicketRestController(TicketService ticketService, TicketRepository ticketRepository, CustomerRepository customerRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/tickets")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        ticket.setId(0);
        return ticketRepository.save(ticket);
    }

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    @PostMapping("/tickets/{ticketId}/assign")
    public ResponseEntity<Ticket> assignEmployeesToTicket(
            @PathVariable int ticketId,
            @RequestBody List<Integer> employeeIds
    ) {
        Ticket updatedTicket = ticketService.assignEmployeesToTicket(ticketId, employeeIds);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/employee/tickets/{ticketId}")
    public ResponseEntity<List<Employee>> getEmployeesForTicket(@PathVariable int ticketId) {
        List<Employee> employees = ticketService.getEmployeesForTicket(ticketId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/tickets/customer/{ticketId}")
    public Customer getCustomerForTicket(@PathVariable int ticketId) {
        return ticketService.getCustomerForTicket(ticketId);
    }

    @GetMapping("/tickets/{ticketId}")
    public Optional<Ticket> getTicketById(@PathVariable int ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @GetMapping("/tickets/employee/{employeeId}")
    public ResponseEntity<List<Ticket>> getTicketsForEmployee(@PathVariable int employeeId) {
        List<Ticket> tickets = ticketService.getTicketsForEmployee(employeeId);
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return ticketRepository.findById(id)
                .map(existingTicket -> {

                    if (updates.containsKey("issueDescription")) {
                        existingTicket.setIssueDescription((String) updates.get("issueDescription"));
                    }
                    if (updates.containsKey("status")) {
                        existingTicket.setStatus((String) updates.get("status"));
                    }
                    if (updates.containsKey("createdDate")) {
                        existingTicket.setCreatedDate(LocalDate.parse((String) updates.get("createdDate")));
                    }
                    if (updates.containsKey("resolvedDate")) {
                        existingTicket.setResolvedDate(LocalDate.parse((String) updates.get("resolvedDate")));
                    }
                    if (updates.containsKey("imagePath")) {
                        existingTicket.setImagePath((String) updates.get("imagePath"));
                    }
                    Ticket updatedTicket = ticketRepository.save(existingTicket);
                    return ResponseEntity.ok(updatedTicket);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}