package ru.shmelev.roomsschedules.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.roomsschedules.dto.request.CreateRoomRequest;
import ru.shmelev.roomsschedules.dto.response.RoomResponse;
import ru.shmelev.roomsschedules.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RoomResponse> create(@Valid @RequestBody CreateRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }
}
