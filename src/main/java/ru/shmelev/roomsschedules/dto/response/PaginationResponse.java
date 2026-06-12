package ru.shmelev.roomsschedules.dto.response;

public record PaginationResponse(
        int page,
        int pageSize,
        long total
) { }