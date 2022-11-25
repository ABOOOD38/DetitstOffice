package models;

import java.time.LocalDate;

public record Schedule(int id, LocalDate startAt, LocalDate endAt) {
    public static ScheduleBuilder getBuilder() {
        return new ScheduleBuilder();
    }

    public static class ScheduleBuilder {
        private int id;
        private LocalDate startAt;
        private LocalDate endAt;

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

        public Schedule build() {
            return new Schedule(id, startAt, endAt);
        }
    }

    @Override
    public String toString() {
        return id + " -> " + startAt.toString() + " -> " + endAt.toString();
    }
}
