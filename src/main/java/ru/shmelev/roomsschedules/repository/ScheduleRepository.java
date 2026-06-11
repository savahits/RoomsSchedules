package ru.shmelev.roomsschedules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.roomsschedules.entity.Schedule;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    boolean existsByRoomId(UUID roomId);
}
