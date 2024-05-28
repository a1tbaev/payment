package kg.example.task1.exception;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException(String msg) {
        super(msg);
    }
}
