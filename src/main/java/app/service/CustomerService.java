package app.service;

import app.domain.Customer;
import app.domain.Ticket;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.CustomerSaveException;
import app.exceptions.CustomerUpdateException;
import app.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository repository = new CustomerRepository();
    private final TicketService ticketService = TicketService.getInstance();

    public Customer save(Customer customer) {
        if (customer == null) {
            throw new CustomerSaveException("Покупатель не может быть null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new CustomerSaveException("Имя покупателя не должно быть пустым");
        }

        customer.setActive(true);
        return repository.save(customer);
    }

    public List<Customer> getAllActiveCustomers() {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();
    }

    public Customer getActiveCustomerById(Long id) {
        Customer customer = repository.findById(id);

        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }

    public void update(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new CustomerUpdateException("Имя покупателя не может быть пустым");
        }

        repository.update(id, newName);
    }

    public void deleteById(Long id) {
        Customer customer = getActiveCustomerById(id);
        customer.setActive(false);
    }

    public void deleteByName(String name) {
        getAllActiveCustomers()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));
    }

    public void restoreById(Long id) {
        Customer customer = repository.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        customer.setActive(true);
    }

    public int getActiveCustomersNumber() {
        return getAllActiveCustomers().size();
    }

    public double getCustomerCartTotalCost(Long customerId) {
        return getActiveCustomerById(customerId)
                .getCart()
                .stream()
                .filter(Ticket::isActive)
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    public double getCustomerCartAveragePrice(Long customerId) {
        return getActiveCustomerById(customerId)
                .getCart()
                .stream()
                .filter(Ticket::isActive)
                .mapToDouble(Ticket::getPrice)
                .average()
                .orElse(0.0);
    }

    public void addProductToCustomerCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomerById(customerId);
        Ticket ticket = ticketService.getActiveTicketById(productId);
        customer.getCart().add(ticket);
    }

    public void removeTicketFromCustomerCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomerById(customerId);
        Ticket ticket = ticketService.getActiveTicketById(productId);
        customer.getCart().remove(ticket);
    }

    public void clearCustomerCart(Long customerId) {
        Customer customer = getActiveCustomerById(customerId);
        customer.getCart().clear();
    }
}
