package ru.shmelev.roomsschedules.repository;

import org.springframework.data.jpa.repository.Query;
import ru.shmelev.roomsschedules.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlotRepository extends JpaRepository<Slot, UUID> {
    Optional<OffsetDateTime>  findMaxEndAtByScheduleId(UUID id);

    @Query("""
    select s
    from Slot s
    where s.room.id = :roomId
      and s.startAt >= :dayStart
      and s.startAt < :dayEnd
      and not exists (
            select b
            from Booking b
            where b.slot = s
              and b.status = 'active'
      )
    order by s.startAt
""")
    List<Slot> findAvailableSlots(
            UUID roomId,
            OffsetDateTime dayStart,
            OffsetDateTime dayEnd
    );

}
