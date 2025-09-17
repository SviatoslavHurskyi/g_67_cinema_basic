package app.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {

    private Long id;
    private String name;
    private boolean active;

    private List<Ticket> cart = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return active == customer.active
                && Objects.equals(id, customer.id)
                && Objects.equals(name, customer.name)
                && Objects.equals(cart, customer.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, cart);
    }

    @Override
    public String toString() {
        return String.format("Customer: id - %d, name - %s, active - %b", id, name, active);
    }
}
