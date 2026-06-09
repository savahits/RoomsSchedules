package ru.shmelev.roomsschedules.dto.response;

import ru.shmelev.roomsschedules.entity.Slot;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SlotResponse(
        UUID id,
        UUID roomId,
        OffsetDateTime start,
        OffsetDateTime end
) {
}