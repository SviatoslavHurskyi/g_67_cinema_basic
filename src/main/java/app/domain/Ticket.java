package app.domain;

import java.util.Objects;

public class Ticket {

    private Long id;
    private String titleMovie;
    private double price;
    private boolean active;
    private Genre genre;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public double getPrice() {
        return price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(price, ticket.price) == 0 && active == ticket.active && Objects.equals(id, ticket.id) && Objects.equals(titleMovie, ticket.titleMovie) && genre == ticket.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titleMovie, price, active, genre);
    }

    @Override
    public String toString() {
        return String.format("Ticket: id - %d, titleMove - %s, genre - %s, price - %.2f, active - %b"
                , id, titleMovie, genre, price, active);
    }

}
