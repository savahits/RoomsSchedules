package ru.shmelev.roomsschedules.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.List;

public record CreateScheduleRequest(

        @Schema(example = "[1,2,3,5]")
        List<Integer> daysOfWeek,

        @Schema(type = "string", example = "10:00")
        LocalTime startTime,

        @Schema(type = "string", example = "18:00")
        LocalTime endTime
) {}
