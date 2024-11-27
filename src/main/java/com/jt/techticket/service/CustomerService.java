package com.jt.techticket.service;

import com.jt.techticket.entity.Customer;

import java.util.List;

public interface CustomerService {

    public Customer addCustomer(Customer customer);
    public List<Customer> findAll();
    public Customer findById(int customerId);
    public void deleteById(int customerId);
    public Customer updateCustomer(int customerId, Customer customer);

}
