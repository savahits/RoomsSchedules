package ru.shmelev.roomsschedules.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.roomsschedules.dto.response.TokenResponse;
import ru.shmelev.roomsschedules.security.JwtService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final JwtService jwtService;

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

    @GetMapping("/_info")
    public ResponseEntity<Void> info() {
        return ResponseEntity.ok().build();
    }
}
