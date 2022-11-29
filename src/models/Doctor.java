package models;

import java.time.LocalDate;

public record Doctor(Integer ID, LoginInfo loginInfo, PersonalInfo personalInfo, Schedule schedule) {

    public static DoctorBuilder getBuilder() {
        return new DoctorBuilder();
    }

    public static class DoctorBuilder {
        private final LoginInfo.LoginInfoBuilder loginInfoBuilder = LoginInfo.getBuilder();
        private final PersonalInfo.PersonInfoBuilder personInfoBuilder = PersonalInfo.getBuilder();
        private final Schedule.ScheduleBuilder scheduleBuilder = Schedule.getBuilder();

        private Integer ID;

        public DoctorBuilder withUserName(String userName) {
            loginInfoBuilder.withUserName(userName);
            return this;
        }

        public DoctorBuilder withPassword(String password) {
            loginInfoBuilder.withPassword(password);
            return this;
        }

        public DoctorBuilder withName(String name) {
            personInfoBuilder.withName(name);
            return this;
        }

        public DoctorBuilder withPhoneNumber(String phoneNumber) {
            personInfoBuilder.withPhoneNumber(phoneNumber);
            return this;
        }

        public DoctorBuilder withEmail(String email) {
            personInfoBuilder.withEmail(email);
            return this;
        }

        public DoctorBuilder withScheduleID(int id) {
            scheduleBuilder.withID(id);
            return this;
        }

        public DoctorBuilder withStartAt(LocalDate startAt) {
            scheduleBuilder.withStartAt(startAt);
            return this;
        }

        public DoctorBuilder withEndAt(LocalDate endAt) {
            scheduleBuilder.withEndAt(endAt);
            return this;
        }

        public DoctorBuilder withID(Integer ID) {
            this.ID = ID;
            return this;
        }

        public Doctor build() {
            return new Doctor(ID, loginInfoBuilder.build(), personInfoBuilder.build(), scheduleBuilder.build());
        }
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "Employee Info" +
                loginInfo.toString() + "\n" +
                "Personal Info" +
                personalInfo.toString() + "\n" +
                "Schedule=" + schedule.toString() +
                '}';
    }
}
