package ru.shmelev.roomsschedules.repository;

import ru.shmelev.roomsschedules.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
}
