package app.repository;

import app.domain.Ticket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//CRUD - Crate, Read, Update, Delete
public class TicketRepository {

    private final List<Ticket> database = new ArrayList<>();
    public Long maxId;

    public Ticket save(Ticket ticket) {
        ticket.setId(++maxId);
        database.add(ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return database;
    }

    public Ticket findById(Long id) {
        for (Ticket ticket : database) {
            if (ticket.getId().equals(id)) {
                return ticket;
            }
        }
        return null;
    }

    public void update(Long id, double newPrice) {
        for (Ticket ticket : database) {
            if (ticket.getId().equals(id)) {
                ticket.setPrice(newPrice);
                break;
            }
        }
    }

    public void deleteById(Long id) {
        Iterator<Ticket> iterator = database.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}
