package ru.shmelev.roomsschedules.dto.response;

import java.util.List;

public record BookingListResponse(
        List<BookingResponse> bookings,
        PaginationResponse pagination
) {
}