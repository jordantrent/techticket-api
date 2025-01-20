package com.jt.techticket.dao;

import com.jt.techticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByEmployees_Id(int employeeId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE SIZE(t.employees) > 0")
    long countAssignedTickets();

    @Query("SELECT COUNT(t) FROM Ticket t WHERE SIZE(t.employees) = 0")
    long countUnassignedTickets();
}
