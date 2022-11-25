package models;

public record Doctor(Employee employee, PersonInfo personInfo, Schedule schedule) {

    public static DoctorBuilder getBuilder() {
        return new DoctorBuilder();
    }

    public static class DoctorBuilder {
        private final Employee.EmployeeBuilder employeeBuilder = Employee.getBuilder();
        private final PersonInfo.PersonInfoBuilder personInfoBuilder = PersonInfo.getBuilder();
        private Schedule schedule;

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

        public DoctorBuilder withSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        public Doctor build() {
            return new Doctor(employeeBuilder.build(), personInfoBuilder.build(), schedule);
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
