package ru.shmelev.roomsschedules.repository;

import ru.shmelev.roomsschedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    boolean existsByRoomId(UUID roomId);
}
