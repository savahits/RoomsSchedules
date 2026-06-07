package ru.shmelev.roomsschedules.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shmelev.roomsschedules.dto.response.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScheduleAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleScheduleExists(
            ScheduleAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(
                        409,
                        "CONFLICT",
                        ex.getMessage()
                ));
    }
}