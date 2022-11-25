package database.dao;

import models.Patient;

import java.sql.SQLException;

public interface PatientDao extends Dao<Patient> {

    boolean insert(Patient patient) throws SQLException;

}
