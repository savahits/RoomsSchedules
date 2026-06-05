CREATE TABLE users (
    id            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL
                               CONSTRAINT chk_users_role CHECK (role IN ('admin', 'user')),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_users_email UNIQUE (email)
);

INSERT INTO users (id, email, password_hash, role) VALUES
    ('00000000-0000-0000-0000-000000000001',
     'admin@booking.local',
     '$2a$12$placeholder_admin_hash',
     'admin'),
    ('00000000-0000-0000-0000-000000000002',
     'user@booking.local',
     '$2a$12$placeholder_user_hash',
     'user');

CREATE TABLE rooms (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    capacity    INT          CHECK (capacity IS NULL OR capacity > 0),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);


CREATE TABLE schedules (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id      UUID        NOT NULL,
    days_of_week INTEGER[]   NOT NULL,
    start_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_schedules_room  FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE,
    CONSTRAINT uq_schedules_room  UNIQUE (room_id),
    CONSTRAINT chk_schedule_times CHECK (end_time > start_time),
    CONSTRAINT chk_schedule_min_duration
        CHECK (end_time - start_time >= INTERVAL '30 minutes')
);


CREATE TABLE slots (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id     UUID        NOT NULL,
    schedule_id UUID        NOT NULL,
    start_at    TIMESTAMPTZ NOT NULL,
    end_at      TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_slots_room     FOREIGN KEY (room_id)     REFERENCES rooms     (id) ON DELETE CASCADE,
    CONSTRAINT fk_slots_schedule FOREIGN KEY (schedule_id) REFERENCES schedules (id) ON DELETE CASCADE,
    CONSTRAINT chk_slots_times   CHECK (end_at > start_at),
    CONSTRAINT chk_slots_duration
        CHECK (end_at - start_at = INTERVAL '30 minutes'),
    EXCLUDE USING gist (
        room_id WITH =,
        tstzrange(start_at, end_at, '[)') WITH &&
    )
);

CREATE INDEX idx_slots_room_start ON slots (room_id, start_at);

CREATE INDEX idx_slots_schedule   ON slots (schedule_id);


CREATE TABLE bookings (
    id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    slot_id         UUID        NOT NULL,
    user_id         UUID        NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'active'
                                CONSTRAINT chk_bookings_status CHECK (status IN ('active', 'cancelled')),
    conference_link VARCHAR(500),
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_bookings_slot FOREIGN KEY (slot_id) REFERENCES slots   (id) ON DELETE RESTRICT,
    CONSTRAINT fk_bookings_user FOREIGN KEY (user_id) REFERENCES users   (id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX uq_slot_active_booking
    ON bookings (slot_id)
    WHERE status = 'active';


CREATE INDEX idx_bookings_user_status ON bookings (user_id, status);


CREATE INDEX idx_bookings_slot ON bookings (slot_id);


CREATE INDEX idx_bookings_created ON bookings (created_at DESC);