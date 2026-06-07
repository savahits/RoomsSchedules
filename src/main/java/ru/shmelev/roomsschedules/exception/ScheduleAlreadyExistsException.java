package ru.shmelev.roomsschedules.exception;

import java.util.UUID;

public class ScheduleAlreadyExistsException extends RuntimeException {
    public ScheduleAlreadyExistsException(UUID roomId) {
        super("Schedule already exists for room: " + roomId);
    }
}
