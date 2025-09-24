package application.main.model.exception;

public class NoSuchAddressTypeException extends RuntimeException {
    public NoSuchAddressTypeException(String message) {
        super(message);
    }
}
