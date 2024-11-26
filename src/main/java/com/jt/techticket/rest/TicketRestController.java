package com.jt.techticket.rest;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.dao.EmployeeRepository;
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

//    TODO these can be final fields
    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private CustomerRepository customerRepository; // TODO not used
    private EmployeeRepository employeeRepository;

    @Autowired
    public TicketRestController(TicketService ticketService, TicketRepository ticketRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
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

                    // Check and update various fields
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

                    // Update the list of employees assigned to the ticket
                    if (updates.containsKey("employees")) {
                        List<Integer> employeeIds = (List<Integer>) updates.get("employees");
                        List<Employee> employees = employeeRepository.findAllById(employeeIds);
                        existingTicket.setEmployees(employees);
                    }

                    // Save the updated ticket
                    Ticket updatedTicket = ticketRepository.save(existingTicket);
                    return ResponseEntity.ok(updatedTicket);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/tickets/{id}")
    public String deleteTicket(@PathVariable int id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ticket id not found - " + id));

        ticketRepository.deleteById(id);

        return "ticket customer id - " + id;
    }


}