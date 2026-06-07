package ru.shmelev.roomsschedules.dto.response;

public record ApiErrorResponse(
        int status,
        String error,
        String message
) {}