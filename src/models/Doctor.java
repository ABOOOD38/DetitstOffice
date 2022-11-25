package models;

import java.time.LocalDate;

public record Doctor(Employee employee, PersonInfo personInfo, Schedule schedule) {

    public static DoctorBuilder getBuilder() {
        return new DoctorBuilder();
    }

    public static class DoctorBuilder {
        private final Employee.EmployeeBuilder employeeBuilder = Employee.getBuilder();
        private final PersonInfo.PersonInfoBuilder personInfoBuilder = PersonInfo.getBuilder();
        private final Schedule.ScheduleBuilder scheduleBuilder = Schedule.getBuilder();

        public DoctorBuilder withUserName(String userName) {
            employeeBuilder.withUserName(userName);
            return this;
        }

        public DoctorBuilder withPassword(String password) {
            employeeBuilder.withPassword(password);
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

        public Doctor build() {
            return new Doctor(employeeBuilder.build(), personInfoBuilder.build(), scheduleBuilder.build());
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "Employee Info" +
                employee.toString() + "\n" +
                "Personal Info" +
                personInfo.toString() + "\n" +
                "Schedule=" + schedule.toString() +
                '}';
    }
}
