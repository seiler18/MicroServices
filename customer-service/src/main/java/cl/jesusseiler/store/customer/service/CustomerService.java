package cl.jesusseiler.store.customer.service;

import java.util.List;

import cl.jesusseiler.store.customer.entity.Customer;
import cl.jesusseiler.store.customer.entity.Region;

public interface CustomerService {

    public List<Customer> findCustomerAll();
    public List<Customer> findCustomersByRegion(Region region);

    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Customer customer);
    public  Customer getCustomer(Long id);



}
