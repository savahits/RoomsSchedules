package ru.shmelev.roomsschedules.repository;

import ru.shmelev.roomsschedules.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
