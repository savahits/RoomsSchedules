package ru.shmelev.roomsschedules.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmelev.roomsschedules.dto.request.CreateRoomRequest;
import ru.shmelev.roomsschedules.dto.response.RoomResponse;
import ru.shmelev.roomsschedules.entity.Room;
import ru.shmelev.roomsschedules.repository.RoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomResponse create(CreateRoomRequest request) {
        Room room = Room.builder()
                .name(request.name())
                .description(request.description())
                .capacity(request.capacity())
                .build();

        return RoomResponse.from(roomRepository.save(room));
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(RoomResponse::from)
                .toList();
    }
}
