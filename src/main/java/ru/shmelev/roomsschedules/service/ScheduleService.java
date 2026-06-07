package ru.shmelev.roomsschedules.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmelev.roomsschedules.dto.request.CreateScheduleRequest;
import ru.shmelev.roomsschedules.dto.response.ScheduleResponse;
import ru.shmelev.roomsschedules.entity.Room;
import ru.shmelev.roomsschedules.entity.Schedule;
import ru.shmelev.roomsschedules.entity.Slot;
import ru.shmelev.roomsschedules.repository.RoomRepository;
import ru.shmelev.roomsschedules.repository.ScheduleRepository;
import ru.shmelev.roomsschedules.repository.SlotRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final RoomRepository roomRepository;
    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;

    @Value("${slots.generation-window-days:30}")
    private int generationWindowDays;

    @Transactional
    public ScheduleResponse create(UUID roomId, CreateScheduleRequest request) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
                        "Room not found: " + roomId));

        if (scheduleRepository.existsByRoomId(roomId)) {
            throw new IllegalStateException(
                    "Schedule already exists for room: " + roomId);
        }

        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException("endTime must be after startTime");
        }

        Schedule schedule = Schedule.builder()
                .room(room)
                .daysOfWeek(request.daysOfWeek().stream()
                        .mapToInt(Integer::intValue)
                        .toArray())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .build();

        schedule = scheduleRepository.save(schedule);

        List<Slot> slots = generateSlots(schedule, LocalDate.now(ZoneOffset.UTC),
                LocalDate.now(ZoneOffset.UTC).plusDays(generationWindowDays));
        slotRepository.saveAll(slots);

        return ScheduleResponse.from(schedule);
    }

    public List<Slot> generateSlots(Schedule schedule, LocalDate from, LocalDate to) {
        List<Slot> slots = new ArrayList<>();


        Set<Integer> scheduleDays = new HashSet<>();
        for (int d : schedule.getDaysOfWeek()) scheduleDays.add(d);

        LocalDate current = from;
        while (!current.isAfter(to)) {

            if (scheduleDays.contains(current.getDayOfWeek().getValue())) {

                LocalTime slotStart = schedule.getStartTime();
                while (slotStart.plusMinutes(30).compareTo(schedule.getEndTime()) <= 0) {
                    LocalTime slotEnd = slotStart.plusMinutes(30);

                    slots.add(Slot.builder()
                            .room(schedule.getRoom())
                            .schedule(schedule)
                            .startAt(current.atTime(slotStart).atOffset(ZoneOffset.UTC))
                            .endAt(current.atTime(slotEnd).atOffset(ZoneOffset.UTC))
                            .build());

                    slotStart = slotEnd;
                }
            }
            current = current.plusDays(1);
        }

        return slots;
    }
}
