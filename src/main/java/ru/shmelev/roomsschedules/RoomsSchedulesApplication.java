package ru.shmelev.roomsschedules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RoomsSchedulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomsSchedulesApplication.class, args);
    }

}
