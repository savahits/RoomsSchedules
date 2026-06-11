package ru.shmelev.roomsschedules.dto.response;

import ru.shmelev.roomsschedules.entity.Booking;

import java.time.OffsetDateTime;
import java.util.UUID;

public record BookingResponse(
        UUID id,
        UUID slotId,
        UUID userId,
        String status,
        OffsetDateTime createdAt
) {

    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getSlot().getId(),
                booking.getUser().getId(),
                booking.getStatus().name(),
                booking.getCreatedAt()
        );
    }

}