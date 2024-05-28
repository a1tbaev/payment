package kg.example.task1.service;

import kg.example.task1.dto.request.AuthRequest;
import kg.example.task1.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthRequest request);
}
