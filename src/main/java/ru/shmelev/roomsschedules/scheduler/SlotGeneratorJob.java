package ru.shmelev.roomsschedules.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.shmelev.roomsschedules.entity.Slot;
import ru.shmelev.roomsschedules.repository.ScheduleRepository;
import ru.shmelev.roomsschedules.repository.SlotRepository;
import ru.shmelev.roomsschedules.service.ScheduleService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlotGeneratorJob {

    private final ScheduleRepository scheduleRepository;
    private final SlotRepository slotRepository;
    private final ScheduleService scheduleService;

    @Value(value = "${slots.generation-window-days:30}")
    private int windowDays;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    @Transactional
    public void extendSlotWindow() {
        log.info("SlotGeneratorJob: начало продления окна слотов");

        scheduleRepository.findAll().forEach(schedule -> {
            OffsetDateTime lastSlotTime = slotRepository
                    .findMaxEndAtByScheduleId(schedule.getId())
                    .orElse(OffsetDateTime.now(ZoneOffset.UTC));

            LocalDate generateFrom = lastSlotTime.toLocalDate().plusDays(1);
            LocalDate generateTo   = LocalDate.now(ZoneOffset.UTC).plusDays(windowDays);

            if (generateFrom.isAfter(generateTo)) {
                return;
            }

            List<Slot> newSlots = scheduleService.generateSlots(
                    schedule, generateFrom, generateTo);

            if (!newSlots.isEmpty()) {
                slotRepository.saveAll(newSlots);
                log.info("SlotGeneratorJob: добавлено {} слотов для расписания {}",
                        newSlots.size(), schedule.getId());
            }
        });

        log.info("SlotGeneratorJob: завершено");
    }
}
