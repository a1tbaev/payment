package kg.example.task1.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Имя пользователя не должна быть пустой!")
        String username,
        @NotBlank(message = "Пароль не должен быть пустым!")
        String password
) {
}
