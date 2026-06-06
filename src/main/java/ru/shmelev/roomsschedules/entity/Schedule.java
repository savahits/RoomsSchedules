package ru.shmelev.roomsschedules.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    // Связь OneToOne — одна переговорка, одно расписание
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false, unique = true)
    private Room room;

    // INTEGER[] в PostgreSQL — через @Column с нужным типом
    // Hibernate маппит int[] напрямую начиная с Hibernate 6
    @Column(name = "days_of_week", nullable = false,
            columnDefinition = "INTEGER[]")
    private int[] daysOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    // Слоты, сгенерированные по этому расписанию
    // mappedBy — не владелец связи, владелец — Slot
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Slot> slots;
}
