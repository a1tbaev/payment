package kg.example.task1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kg.example.task1.config.jwt.JwtTokenBlacklist;
import kg.example.task1.config.jwt.JwtUtil;
import kg.example.task1.dto.request.AuthRequest;
import kg.example.task1.dto.response.AuthenticationResponse;
import kg.example.task1.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "The User Authentication API")
@RequestMapping("/api/auth")
public class AuthenticationApi {
    private final JwtTokenBlacklist tokenBlacklist;
    private final AuthenticationService authenticationService;

    @Operation(summary = "User authentication", description = "This method to authenticate the user")
    @PostMapping("/sign_in")
    public AuthenticationResponse authenticate (@RequestBody AuthRequest authRequest){
        return authenticationService.authenticate(authRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String jwtToken = JwtUtil.getJwtFromRequest(request);
        if (jwtToken != null) {
            tokenBlacklist.blacklistToken(jwtToken);
        }
        return ResponseEntity.ok("Successfully logged out");
    }
}
