package kg.example.task1.exception.exeptions;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException(String msg) {
        super(msg);
    }
}
