package com.jt.techticket.rest;

import com.jt.techticket.dao.TicketRepository;
import com.jt.techticket.entity.Employee;
import com.jt.techticket.entity.Ticket;
import com.jt.techticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketRestController {

    private TicketService ticketService;
    private TicketRepository ticketRepository;
    @Autowired
    public TicketRestController(TicketService ticketService, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
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

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<List<Employee>> getEmployeesForTicket(@PathVariable int ticketId) {
        List<Employee> employees = ticketService.getEmployeesForTicket(ticketId);
        return ResponseEntity.ok(employees);
    }
}