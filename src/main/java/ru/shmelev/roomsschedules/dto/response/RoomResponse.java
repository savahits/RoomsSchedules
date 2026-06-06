package ru.shmelev.roomsschedules.dto.response;

import ru.shmelev.roomsschedules.entity.Room;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RoomResponse(
        UUID id,
        String name,
        String description,
        Integer capacity,
        OffsetDateTime createdAt
) {

    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getCapacity(),
                room.getCreatedAt()
        );
    }
}