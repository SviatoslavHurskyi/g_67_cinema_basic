package app.exceptions;

public class TicketUpdateException extends RuntimeException {
    public TicketUpdateException(String message) {
        super(message);
    }
}
