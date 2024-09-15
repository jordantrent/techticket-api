package com.jt.techticket.service;

import com.jt.techticket.entity.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();
    public Customer findById(int id);
    public void save(Customer customer);
    public void deleteById(int id);

}
