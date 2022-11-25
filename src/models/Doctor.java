package models;

public record Doctor(Employee employee, PersonInfo personInfo, Schedule schedule) {
    public Doctor(Employee employee, PersonInfo personInfo) {
        this(employee, personInfo, null);
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
