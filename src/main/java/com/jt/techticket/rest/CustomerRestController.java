package com.jt.techticket.rest;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {

                    if (updates.containsKey("name")) {
                        existingCustomer.setName((String) updates.get("name"));
                    }
                    if (updates.containsKey("address")) {
                        existingCustomer.setAddress((String) updates.get("address"));
                    }
                    if (updates.containsKey("phone")) {
                        existingCustomer.setPhone((String) updates.get("phone"));
                    }
                    if (updates.containsKey("email")) {
                        existingCustomer.setEmail((String) updates.get("email"));
                    }

                    Customer updatedCustomer = customerRepository.save(existingCustomer);
                    return ResponseEntity.ok(updatedCustomer);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer id not found - " + customerId));

        customerRepository.deleteById(customerId);

        return "Deleted customer id - " + customerId;
    }

}

