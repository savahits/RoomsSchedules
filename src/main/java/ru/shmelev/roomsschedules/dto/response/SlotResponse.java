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


    public static SlotResponse from(Slot slot) {
        return new SlotResponse(
                slot.getId(),
                slot.getRoom().getId(),
                slot.getStartAt(),
                slot.getEndAt()
        );
    }


}