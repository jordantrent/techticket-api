package com.jt.techticket.rest;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    CustomerRepository customerRepository;
    @Autowired
    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer) {
        customer.setId(0);
        return customerRepository.save(customer);
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Employee id not found - " + customerId));

        customerRepository.deleteById(customerId);

        return "Deleted employee id - " + customerId;
    }

}

