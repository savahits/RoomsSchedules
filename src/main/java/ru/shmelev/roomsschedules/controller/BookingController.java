package ru.shmelev.roomsschedules.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.roomsschedules.dto.response.BookingListResponse;
import ru.shmelev.roomsschedules.dto.response.BookingResponse;
import ru.shmelev.roomsschedules.service.BookingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public BookingResponse createBooking(@RequestParam UUID slotId, Authentication authentication) throws BadRequestException {

        UUID userId = (UUID) authentication.getPrincipal();

        return bookingService.createBooking(slotId, userId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('admin')")
    public BookingListResponse listBookings(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        return bookingService.getAllBookings(page, pageSize);
    }

    @PostMapping("/{bookingId}/cancel")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable UUID bookingId,
            Authentication authentication) {

        UUID userId = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId, userId));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('user')")
    public List<BookingResponse> getMyBookings(Authentication authentication) {

        UUID userId = (UUID) authentication.getPrincipal();

        return bookingService.getBookingsByUser(userId);

    }

}