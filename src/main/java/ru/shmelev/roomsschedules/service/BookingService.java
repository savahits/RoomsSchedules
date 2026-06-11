package ru.shmelev.roomsschedules.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ru.shmelev.roomsschedules.dto.response.BookingResponse;
import ru.shmelev.roomsschedules.entity.Booking;
import ru.shmelev.roomsschedules.entity.Slot;
import ru.shmelev.roomsschedules.entity.User;
import ru.shmelev.roomsschedules.repository.BookingRepository;
import ru.shmelev.roomsschedules.repository.SlotRepository;
import ru.shmelev.roomsschedules.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private  final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, SlotRepository slotRepository,  UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.slotRepository = slotRepository;
        this.userRepository = userRepository;
    }

    public BookingResponse createBooking(UUID slotId, UUID userId) throws BadRequestException {

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new EntityNotFoundException("Слот не найден"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь не найден"));

        if (bookingRepository.existsActiveBookingBySlotId(slotId)) {
            throw new IllegalArgumentException("Слот уже занят");
        }

        if (slot.getStartAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Неверный запрос");
        }

        Booking booking = Booking.builder()
                .slot(slot)
                .user(user)
                .status(Booking.Status.active)
                .build();

        booking = bookingRepository.save(booking);

        return new BookingResponse(
                booking.getId(),
                booking.getSlot().getId(),
                booking.getUser().getId(),
                "active",
                booking.getCreatedAt()
        );


    }

}
