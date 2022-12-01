package models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public record Appointment(Integer ID, LocalDate app_date, LocalTime startTime, Integer duration) {

}
