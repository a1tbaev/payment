package kg.example.task1.service.impl;

import kg.example.task1.config.jwt.JwtService;
import kg.example.task1.dto.request.AuthRequest;
import kg.example.task1.dto.response.AuthenticationResponse;
import kg.example.task1.entity.User;
import kg.example.task1.exception.BadCredentialException;
import kg.example.task1.repo.UserRepository;
import kg.example.task1.service.AuthenticationService;
import kg.example.task1.service.LoginAttemptService;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    @Override
    public AuthenticationResponse authenticate(AuthRequest request) {
        if (loginAttemptService.isBlocked(request.username())) {
            throw new BadCredentialException("User account is locked due to too many failed login attempts");
        }

        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> {
            String message = "User with id: " + request.username() + "is not found";
            return new EntityNotFoundException(message);
        });

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            loginAttemptService.loginFailed(request.username());
            throw new BadCredentialsException("wrong password please provide right credentials");
        }

        loginAttemptService.loginSucceeded(request.username());
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }
}
