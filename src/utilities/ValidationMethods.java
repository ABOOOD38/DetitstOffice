package utilities;

import database.dao.ConcretePatientDao;
import models.Patient;
import models.Schedule;

import java.sql.SQLException;
import java.sql.Time;
import java.time.Period;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationMethods {

    public static boolean isValidPhoneNumber(String number) {
        return number.length() == 12 && number.chars().allMatch(Character::isDigit) && number.startsWith("962");
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidPatient(Patient patient) throws SQLException {
        Collection<Patient> patients = ConcretePatientDao.getInstance().getAll();
        for (Patient _patient : patients)
            if (patient.personalInfo().equalsNameAndPhoneNum(_patient.personalInfo())) {
                return false;
            }
        return true;
    }

    public static boolean isValidDate(Schedule schedule) {
        Period period = Period.between(schedule.startAt(), schedule.endAt());
        return period.getDays() >= 7;
    }

    //1 if start same end, 2 if start > end, 3 good.
    public static int isValidTime(Time startTime, Time endTime) {
        long diffMs = endTime.getTime() - startTime.getTime();
        long minutes = diffMs / 1000 / 60;
        System.err.println(minutes);

        if (minutes == 0)
            return 1;
        if (minutes < 0)
            return 2;

        return 3;
    }

    public static boolean isValidLength(String str) {
        return str.length() >= 6;
    }

}
