package com.jt.techticket.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TicketID")
    private int id;

    @Column(name="IssueDescription")
    private String issueDescription;

    @Column(name="Status")
    private String status;

    @Column(name="CreatedDate")
    private LocalDate createdDate;

    @Column(name="ResolvedDate")
    private LocalDate resolvedDate;

    @Column(name="ImagePath")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="employeeticket", joinColumns = @JoinColumn(name = "TicketID"), inverseJoinColumns = @JoinColumn(name = "EmployeeID"))
    private List<Employee> employees;

    public Ticket() {}
    @JsonCreator
    public Ticket(@JsonProperty("issueDescription")String issueDescription, @JsonProperty("status")String status,@JsonProperty("imagePath") String imagePath) {
        this.issueDescription = issueDescription;
        this.status = status;
        this.createdDate = LocalDate.now();
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDate resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
