package ru.shmelev.roomsschedules.dto.response;

import java.util.List;

public record SlotsResponse (
        List<SlotResponse> slots
) { }