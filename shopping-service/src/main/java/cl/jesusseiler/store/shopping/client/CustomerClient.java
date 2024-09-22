package cl.jesusseiler.store.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.jesusseiler.store.shopping.model.Customer;

@FeignClient(name = "customer-service", path = "/customers") 
public interface CustomerClient {

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
    
}
