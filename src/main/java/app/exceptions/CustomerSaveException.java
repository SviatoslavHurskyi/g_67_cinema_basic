package app.exceptions;

public class CustomerSaveException extends RuntimeException {
    public CustomerSaveException(String message) {
        super(message);
    }
}
