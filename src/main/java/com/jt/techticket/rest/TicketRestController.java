package com.jt.techticket.rest;

import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Employee;
import com.jt.techticket.entity.Ticket;
import com.jt.techticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TicketRestController {

    private final TicketService ticketService;

    @Autowired
    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/tickets")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
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
    public Ticket getTicketById(@PathVariable int ticketId) {
        return ticketService.findById(ticketId);
    }

    @GetMapping("/tickets/employee/{employeeId}")
    public List <Ticket> getTicketsForEmployee(@PathVariable int employeeId) {
        return ticketService.getTicketsForEmployee(employeeId);
    }

    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        try {
            Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);
            return ResponseEntity.ok(updatedTicket);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tickets/{ticketId}")
    public String deleteEmployee(@PathVariable int ticketId) {
        ticketService.deleteById(ticketId);
        return "Deleted customer id - " + ticketId;
    }

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> getTicketCounts() {
        Map<String, Long> counts = ticketService.getTicketCounts();
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/in-progress")
    public List<Ticket> getInProgressTickets() {
        return ticketService.getInProgressTickets();
    }


}