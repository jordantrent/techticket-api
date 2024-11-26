package com.jt.techticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="employees")
public class Employee {

    //   TODO Space between fields in this file is inconsistent with your other files. Maybe consider something like sonarlint to help you keep things consistent across your project.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    // TODO Is there a reason you didn't just call this employeeId to keep your objects consistent with your database?
    private int id;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Position")
    private String position;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Email")
    private String email;
    @Column(name = "Photo")
    private String photo;
    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Ticket> tickets;

    public Employee(String firstName, String lastName, String position, String phone, String email, String photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
    }

    public Employee() {}

    public int getId() {
        return id;
    }

    @JsonIgnore
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
