package com.jt.techticket.service;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//TODO: I will add validation at some point!

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Did not find customer id - " + customerId));
    }

    @Override
    public void deleteById(int customerId) {
        customerRepository.deleteById(customerId);
    }

    //TODO: currently unsure if these update methods work 100% as intended, they are WIP.
    @Transactional
    public Customer updateCustomer(int customerId, Customer customerDetails) {

        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        if (customerDetails.getName() != null && !customerDetails.getName().isEmpty()) {
            existingCustomer.setName(customerDetails.getName());
        }
        if (customerDetails.getAddress() != null && !customerDetails.getAddress().isEmpty()) {
            existingCustomer.setAddress(customerDetails.getAddress());
        }
        if (customerDetails.getPhone() != null && !customerDetails.getPhone().isEmpty()) {
            existingCustomer.setPhone(customerDetails.getPhone());
        }
        if (customerDetails.getEmail() != null && !customerDetails.getEmail().isEmpty()) {
            existingCustomer.setEmail(customerDetails.getEmail());
        }

        return customerRepository.save(existingCustomer);
    }


}
