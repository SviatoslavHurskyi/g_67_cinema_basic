package app.exceptions;

public class TicketSaveException extends RuntimeException {
    public TicketSaveException(String message) {
        super(message);
    }
}
