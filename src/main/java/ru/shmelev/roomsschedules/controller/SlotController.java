package ru.shmelev.roomsschedules.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.roomsschedules.dto.response.SlotsResponse;
import ru.shmelev.roomsschedules.service.SlotService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("rooms/{roomId}/slots/lits")
    public ResponseEntity<SlotsResponse> getSlotsByRoomAndDate(@PathVariable UUID roomId, @RequestParam LocalDate date) {

        return ResponseEntity.ok(slotService.getAvailableSlots(roomId, date));

    }

}
