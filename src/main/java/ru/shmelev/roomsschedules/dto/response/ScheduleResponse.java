package ru.shmelev.roomsschedules.dto.response;

import ru.shmelev.roomsschedules.entity.Schedule;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record ScheduleResponse(
        UUID id,
        UUID roomId,
        List<Integer> daysOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        OffsetDateTime createdAt
) {
    public static ScheduleResponse from(Schedule schedule) {
        List<Integer> days = Arrays.stream(schedule.getDaysOfWeek())
                .boxed()
                .toList();

        return new ScheduleResponse(
                schedule.getId(),
                schedule.getRoom().getId(),
                days,
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getCreatedAt()
        );
    }
}
