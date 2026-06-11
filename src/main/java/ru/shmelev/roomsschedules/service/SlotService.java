package ru.shmelev.roomsschedules.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.shmelev.roomsschedules.dto.response.SlotResponse;
import ru.shmelev.roomsschedules.dto.response.SlotsResponse;
import ru.shmelev.roomsschedules.repository.RoomRepository;
import ru.shmelev.roomsschedules.repository.SlotRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    private final RoomRepository roomRepository;

    public SlotService(SlotRepository slotRepository, RoomRepository roomRepository) {
        this.slotRepository = slotRepository;
        this.roomRepository = roomRepository;
    }

    public SlotsResponse getAvailableSlots(UUID roomId, LocalDate date) {

        if (!roomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Переговорка не найдена");
        }

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
