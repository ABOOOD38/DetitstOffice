package models;

import java.sql.Time;
import java.time.LocalDate;

public record Schedule(int id, LocalDate startAt, LocalDate endAt, String days, Time startTime, Time endTime) {
    public static ScheduleBuilder getBuilder() {
        return new ScheduleBuilder();
    }

    public static class ScheduleBuilder {
        private int id;
        private LocalDate startAt;
        private LocalDate endAt;
        private String days;
        private Time startTime;
        private Time endTime;


        public ScheduleBuilder withID(int id) {
            this.id = id;
            return this;
        }

        public ScheduleBuilder withStartAt(LocalDate startAt) {
            this.startAt = startAt;
            return this;
        }

        public ScheduleBuilder withEndAt(LocalDate endAt) {
            this.endAt = endAt;
            return this;
        }

        public ScheduleBuilder withDays(String days) {
            this.days = days;
            return this;
        }

        public ScheduleBuilder withStartTime(Time startTime) {
            this.startTime = startTime;
            return this;
        }

        public ScheduleBuilder withEndTime(Time endTime) {
            this.endTime = endTime;
            return this;
        }

        public Schedule build() {
            return new Schedule(id, startAt, endAt, days, startTime, endTime);
        }
    }

    @Override
    public String toString() {
        return id + " -> " + startAt.toString() + " -> " + endAt.toString();
    }
}
