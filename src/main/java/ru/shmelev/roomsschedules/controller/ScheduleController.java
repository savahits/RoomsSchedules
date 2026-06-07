package ru.shmelev.roomsschedules.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.roomsschedules.dto.request.CreateScheduleRequest;
import ru.shmelev.roomsschedules.dto.response.ScheduleResponse;
import ru.shmelev.roomsschedules.service.ScheduleService;

import java.util.UUID;

@RestController
@RequestMapping("/rooms/{roomId}/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ScheduleResponse> create(
            @PathVariable UUID roomId,
            @Valid @RequestBody CreateScheduleRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.create(roomId, request));
    }
}
