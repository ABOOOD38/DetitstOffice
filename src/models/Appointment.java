package models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public record Appointment(LocalDate date, LocalTime startTime, Duration duration) {
}
