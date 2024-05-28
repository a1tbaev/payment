package kg.example.task1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kg.example.task1.config.jwt.JwtTokenBlacklist;
import kg.example.task1.dto.request.AuthRequest;
import kg.example.task1.dto.response.AuthenticationResponse;
import kg.example.task1.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "The User Authentication API")
@RequestMapping("/api/auth")
public class Authentication {
    private final JwtTokenBlacklist tokenBlacklist;
    private final AuthenticationService service;

    @Operation(summary = "User authentication", description = "This method to authenticate the user")
    @PostMapping("/sign_in")
    public AuthenticationResponse authenticate (@RequestBody AuthRequest authRequest){
        return service.authenticate(authRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = extractToken(request);
        tokenBlacklist.blacklistToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
