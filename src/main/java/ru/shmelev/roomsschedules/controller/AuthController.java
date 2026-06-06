package ru.shmelev.roomsschedules.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.roomsschedules.response.TokenResponse;
import ru.shmelev.roomsschedules.security.JwtService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    // Фиксированные UUID — одинаковы для всех запросов с одной ролью.
    // Тесты Авито стабильно получают один user_id → можно проверить владельца брони.
    private static final UUID ADMIN_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID  = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @PostMapping("/dummyLogin")
    public ResponseEntity<TokenResponse> dummyLogin(@RequestParam String role) {
        UUID userId = switch (role.toLowerCase()) {
            case "admin" -> ADMIN_ID;
            case "user"  -> USER_ID;
            default -> throw new IllegalArgumentException("Role must be 'admin' or 'user'");
        };

        String token = jwtService.generateToken(userId, role.toLowerCase());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    // /_info — всегда 200, нужен для GitHub Actions healthcheck
    @GetMapping("/_info")
    public ResponseEntity<Void> info() {
        return ResponseEntity.ok().build();
    }
}
