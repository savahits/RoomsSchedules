package ru.shmelev.roomsschedules.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shmelev.roomsschedules.dto.response.BookingResponse;
import ru.shmelev.roomsschedules.entity.User;
import ru.shmelev.roomsschedules.service.BookingService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{slotId}")
    public BookingResponse createBooking(@PathVariable UUID slotId, Authentication authentication) throws BadRequestException {

        UUID userId = (UUID) authentication.getPrincipal();

        return bookingService.createBooking(slotId, userId);
    }
}