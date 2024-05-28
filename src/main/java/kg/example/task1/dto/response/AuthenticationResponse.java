package kg.example.task1.dto.response;

import kg.example.task1.entity.enums.Role;
import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String username,
        Role role,
        String token
) {
}
