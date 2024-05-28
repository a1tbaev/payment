package kg.example.task1.service;

public interface LoginAttemptService {
    void loginSucceeded(String key);
    void loginFailed(String key);
    boolean isBlocked(String key);

}
