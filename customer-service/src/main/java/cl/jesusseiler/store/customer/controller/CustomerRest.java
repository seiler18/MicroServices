package cl.jesusseiler.store.customer.controller;


import cl.jesusseiler.store.customer.entity.Customer;
import cl.jesusseiler.store.customer.entity.Region;
import cl.jesusseiler.store.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerRest {

    @Autowired
    CustomerService customerService;

    // -------------------Retrieve All
    // Customers--------------------------------------------

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(
            @RequestParam(name = "regionId", required = false) Long regionId) {
        List<Customer> customers = new ArrayList<>();
        if (null == regionId) {
            customers = customerService.findCustomerAll();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            Region Region = new Region();
            Region.setId(regionId);
            customers = customerService.findCustomersByRegion(Region);
            if (null == customers) {
                log.error("Customers with Region id {} not found.", regionId);
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(customers);
    }

    // -------------------Retrieve Single
    // Customer------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (null == customer) {
            log.error("Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    // -------------------Create a
    // Customer-------------------------------------------

    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result)
            throws MethodArgumentNotValidException {
        log.info("Creating Customer : {}", customer);
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result); // Lanza la excepción para ser manejada por el
                                                                     // GlobalExceptionHandler
        }
        //validar si es que el cliente ya existe en la bd
        if (customerService.getCustomer(customer.getId()) != null) {
            log.error("Unable to create. A Customer with numberID {} already exist", customer.getNumberID());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Customer customerDB = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
    }

    // ------------------- Update a Customer
    // ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        log.info("Updating Customer with id {}", id);

        Customer currentCustomer = customerService.getCustomer(id);

        if (null == currentCustomer) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(currentCustomer);
    }

    // ------------------- Delete a
    // Customer-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Customer customer = customerService.getCustomer(id);
        if (null == customer) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return ResponseEntity.ok(customer);
    }

}
