package ru.shmelev.roomsschedules.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.roomsschedules.dto.response.BookingResponse;
import ru.shmelev.roomsschedules.service.BookingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create/{slotId}")
    public BookingResponse createBooking(@PathVariable UUID slotId, Authentication authentication) throws BadRequestException {

        UUID userId = (UUID) authentication.getPrincipal();

        return bookingService.createBooking(slotId, userId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('admin')")
    public List<BookingResponse> listBookings() throws BadRequestException {
        return bookingService.getAllBookings();
    }
}