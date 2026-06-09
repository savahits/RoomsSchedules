package ru.shmelev.roomsschedules.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.shmelev.roomsschedules.dto.response.ApiErrorResponse;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler
        implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ApiErrorResponse body =
                new ApiErrorResponse(
                        403,
                        "FORBIDDEN",
                        "Access denied"
                );

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}