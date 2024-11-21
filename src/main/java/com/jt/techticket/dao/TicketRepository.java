package com.jt.techticket.dao;

import com.jt.techticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByEmployees_Id(int employeeId);
}
