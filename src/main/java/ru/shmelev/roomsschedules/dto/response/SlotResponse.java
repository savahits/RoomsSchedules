package ru.shmelev.roomsschedules.dto.response;

import java.time.Instant;
import java.util.UUID;

public record SlotResponse(
        UUID id,
        UUID roomId,
        Instant start,
        Instant end
) {
}