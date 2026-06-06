package ru.shmelev.roomsschedules.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRoomRequest(

        @NotBlank(message = "Название переговорки обязательно")
        @Size(max = 255)
        String name,

        String description,

        @Min(value = 1, message = "Вместимость должна быть больше 0")
        Integer capacity
) {}
