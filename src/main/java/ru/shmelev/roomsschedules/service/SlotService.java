package ru.shmelev.roomsschedules.service;


import org.springframework.stereotype.Service;
import ru.shmelev.roomsschedules.dto.response.SlotResponse;
import ru.shmelev.roomsschedules.dto.response.SlotsResponse;
import ru.shmelev.roomsschedules.repository.SlotRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public SlotService(final SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public SlotsResponse getAvailableSlots(UUID roomId, LocalDate date
    ) {
        OffsetDateTime dayStart = date.atStartOfDay().atOffset(ZoneOffset.UTC);

        OffsetDateTime dayEnd = dayStart.plusDays(1);

        List<SlotResponse> slots = slotRepository
                .findAvailableSlots(roomId, dayStart, dayEnd)
                .stream()
                .map(SlotResponse::from)
                .toList();

        return new SlotsResponse(slots);
    }

}
