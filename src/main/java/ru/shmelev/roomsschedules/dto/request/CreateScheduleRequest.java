package ru.shmelev.roomsschedules.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record CreateScheduleRequest(

        @NotEmpty(message = "Укажите хотя бы один день недели")
        List<Integer> daysOfWeek,

        @NotNull(message = "Время начала обязательно")
        LocalTime startTime,

        @NotNull(message = "Время окончания обязательно")
        LocalTime endTime
) {}
