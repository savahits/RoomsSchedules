package ru.shmelev.roomsschedules.repository;

import ru.shmelev.roomsschedules.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
