package app.service;

import app.domain.Customer;
import app.domain.Genre;
import app.domain.Ticket;
import app.exceptions.TicketNotFoundException;
import app.exceptions.TicketSaveException;
import app.exceptions.TicketUpdateException;
import app.repository.TicketRepository;

import java.util.List;

public class TicketService {

    private static TicketService instance;
    private final TicketRepository repository = new TicketRepository();

    private TicketService() {
    }

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public Ticket save(Ticket ticket) {
        if (ticket == null) {
            throw new TicketSaveException("Билет не может быть null");
        }

        String title = ticket.getTitleMovie();
        if (title == null || title.trim().isEmpty()) {
            throw new TicketSaveException("Наименование фильма не должно быть пустым");
        }

        if (ticket.getPrice() < 0) {
            throw new TicketSaveException("Цена билета не должна быть отрицательной");
        }

        if (ticket.getGenre() == null) {
            throw new TicketSaveException("Жанр билета не может быть null");
        }
        ticket.setActive(true);
        return repository.save(ticket);
    }

    public List<Ticket> getAllActiveTickets() {
        return repository.findAll()
                .stream()
                .filter(Ticket::isActive)
                .toList();
    }

    public Ticket getActiveTicketById(Long id) {
        Ticket ticket = repository.findById(id);

        if (ticket == null || !ticket.isActive()) {
            throw new TicketNotFoundException(id);
        }
        return ticket;
    }

    public void update(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new TicketUpdateException("Цена билета не должна быть отрицательной");
        }

        repository.update(id, newPrice);
    }

    public void deleteById(Long id) {
        Ticket ticket = getActiveTicketById(id);
        ticket.setActive(false);
    }

    public void deleteByTitle(String title) {
        getAllActiveTickets()
                .stream()
                .filter(x -> x.getTitleMovie().equals(title))
                .forEach(x -> x.setActive(false));
    }

    public void restoreById(Long id) {
        Ticket product = repository.findById(id);

        if (product == null) {
            throw new TicketNotFoundException(id);
        }

        product.setActive(true);
    }

    public int getActiveTicketsCount() {
        return getAllActiveTickets().size();
    }

    public double getActiveTicketsTotalCost() {
        return getAllActiveTickets()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();
    }

    public double getActiveTicketsAveragePrice() {
        return getAllActiveTickets()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .average()
                .orElse(0.0);
    }

    public List<Ticket> getActiveTicketsByGenre(Genre genre) {
        return getAllActiveTickets()
                .stream()
                .filter(x -> x.getGenre().equals(genre))
                .toList();
    }
}