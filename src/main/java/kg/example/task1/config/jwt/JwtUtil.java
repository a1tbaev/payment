package kg.example.task1.config.jwt;

import jakarta.servlet.http.HttpServletRequest;

public class JwtUtil {
    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
