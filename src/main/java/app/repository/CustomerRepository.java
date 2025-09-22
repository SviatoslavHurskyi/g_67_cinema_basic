package app.repository;

import app.domain.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepository {

    private final Map<Long, Customer> database = new HashMap<>();
    private long maxId;

    public Customer save(Customer customer) {
        customer.setId(++maxId);
        database.put(maxId, customer);
        return customer;
    }

    public List<Customer> findAll() {
        return new ArrayList<>(database.values());
    }

    public Customer findById(Long id) {
        return database.get(id);
    }

    public void update(Long id, String newName) {
        Customer customer = database.get(id);
        if (customer != null) {
            customer.setName(newName);
        }
    }

    public void deleteById(Long id) {
        database.remove(id);
    }
}
