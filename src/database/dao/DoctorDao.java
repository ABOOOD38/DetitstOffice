package database.dao;

import models.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DoctorDao extends Dao<Doctor> {

    ResultSet getDoctorJoinLogin() throws SQLException;

    ResultSet getDoctorJoinSchedule() throws SQLException;

    Integer update(Integer id, Integer fk) throws SQLException;

}
