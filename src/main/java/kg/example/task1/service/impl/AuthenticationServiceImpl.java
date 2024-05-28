package kg.example.task1.service.impl;

import kg.example.task1.config.jwt.JwtService;
import kg.example.task1.dto.request.AuthRequest;
import kg.example.task1.dto.response.AuthenticationResponse;
import kg.example.task1.entity.User;
import kg.example.task1.exception.exeptions.NotFoundException;
import kg.example.task1.repo.UserRepository;
import kg.example.task1.service.AuthenticationService;
import org.springframework.stereotype.Service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    private User getAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByUsername(login).orElseThrow(() -> new NotFoundException("пользователь не найден"));
    }
    @Override
    public AuthenticationResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> {
            String message = "User with id: " + request.username() + "is not found";
            return new EntityNotFoundException(message);
        });

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("wrong password please provide right credentials");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }
}
