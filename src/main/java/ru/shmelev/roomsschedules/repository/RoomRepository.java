package ru.shmelev.roomsschedules.repository;

import ru.shmelev.roomsschedules.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository  extends JpaRepository<Room, Long> {
}
