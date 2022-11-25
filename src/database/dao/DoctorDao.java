package database.dao;

import models.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DoctorDao extends Dao<Doctor> {
    boolean insert(Doctor doctor) throws SQLException;

    ResultSet getDoctorJoinLogin() throws SQLException;

    ResultSet getDoctorJoinSchedule() throws SQLException;
}
