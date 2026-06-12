package ru.shmelev.roomsschedules.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.shmelev.roomsschedules.dto.response.BookingListResponse;
import ru.shmelev.roomsschedules.dto.response.BookingResponse;
import ru.shmelev.roomsschedules.dto.response.PaginationResponse;
import ru.shmelev.roomsschedules.entity.Booking;
import ru.shmelev.roomsschedules.entity.Slot;
import ru.shmelev.roomsschedules.entity.User;
import ru.shmelev.roomsschedules.repository.BookingRepository;
import ru.shmelev.roomsschedules.repository.SlotRepository;
import ru.shmelev.roomsschedules.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;
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

    public BookingResponse createBooking(UUID slotId, UUID userId) {

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

    public BookingListResponse getAllBookings(int page, int pageSize) {

        if (page < 1) {
            throw new IllegalArgumentException("page должен быть >= 1");
        }

        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("pageSize должен быть от 1 до 100");
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Booking> bookingPage =
                bookingRepository.findAll(pageable);

        List<BookingResponse> bookings =
                bookingPage.getContent()
                        .stream()
                        .map(BookingResponse::from)
                        .toList();

        return new BookingListResponse(
                bookings,
                new PaginationResponse(
                        page,
                        pageSize,
                        bookingPage.getTotalElements()
                )
        );
    }

    public BookingResponse cancelBooking(UUID bookingId, UUID userId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Бронь не найдена"));

        if (!booking.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Нельзя отменить чужую бронь");
        }

        if (booking.getStatus() == Booking.Status.cancelled) {
            return BookingResponse.from(booking);
        }

        booking.setStatus(Booking.Status.cancelled);

        booking = bookingRepository.save(booking);

        return BookingResponse.from(booking);
    }

}
