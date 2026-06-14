package ru.shmelev.roomsschedules.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.roomsschedules.entity.Booking;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsActiveBookingBySlotId(UUID slotId);

    List<Booking> findAllByUser_IdAndSlot_StartAtGreaterThanEqual(UUID userId, OffsetDateTime startAt);

}
