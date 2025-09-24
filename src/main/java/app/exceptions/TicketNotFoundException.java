package app.exceptions;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super(String.format("Покупатель с ID %d не найден", id));
    }
}
