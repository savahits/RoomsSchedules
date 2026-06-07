package ru.shmelev.roomsschedules.repository;

import ru.shmelev.roomsschedules.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<OffsetDateTime>  findMaxEndAtByScheduleId(UUID id);
}
