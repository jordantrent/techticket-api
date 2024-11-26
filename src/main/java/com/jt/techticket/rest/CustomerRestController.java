package com.jt.techticket.rest;

import com.jt.techticket.dao.CustomerRepository;
import com.jt.techticket.entity.Customer;
import com.jt.techticket.entity.Employee; // TODO unused import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

//    TODO your controller shouldn't be accessing the database directly - that's what the services are for.
//     You want to abstract your database access away from the controller so if you ever wanted to change your database configuration your public controller wouldn't need to be changed
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

//    TODO I feel like this would be clearer to read like this rather than having lots of chained method calls
    @PutMapping("/customers/{id}")
//    TODO Why does this return a ResponseEntity<Customer> and not just a Customer? All your other endpoints return just the object.
    // TODO Could Object be String here instead of having to cast each of them later as you know they must all be strings?
//      or you could create a specific CustomerUpdate object that has all the fields you want to update
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Map<String, Object> updates) {
//        TODO e.g. as above - al this processing should be done in the service layer

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer id not found - " + id));

        if (updates.containsKey("name")) {
            customer.setName((String) updates.get("name"));
        }
        if (updates.containsKey("address")) {
            customer.setAddress((String) updates.get("address"));
        }
        if (updates.containsKey("phone")) {
            customer.setPhone((String) updates.get("phone"));
        }
        if (updates.containsKey("email")) {
            customer.setEmail((String) updates.get("email"));
        }

        Customer updatedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
//        TODO maybe consider a custom exception here like CustomerNotFoundException
        customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer id not found - " + customerId));

        customerRepository.deleteById(customerId);

        return "Deleted customer id - " + customerId;
    }

}

