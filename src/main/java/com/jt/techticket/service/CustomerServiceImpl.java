package com.jt.techticket.service;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO this isn't actually used at all??
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Did not find customer id - " + id));
    }

    @Override
    public void save(Customer customer) {
        this.customerRepository.save(customer);
    }

    @Override
    public void deleteById(int id) {
        this.customerRepository.deleteById(id);
    }
}
