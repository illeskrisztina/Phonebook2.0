package application.main.model.exception;

import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
