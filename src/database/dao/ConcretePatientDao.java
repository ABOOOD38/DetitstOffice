package database.dao;

import database.Database;
import models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConcretePatientDao implements PatientDao {

    private static final ConcretePatientDao INSTANCE = new ConcretePatientDao();

    private ConcretePatientDao() {

    }

    @Override
    public ResultSet getAll() throws SQLException {
        Database database = Database.getInstance();
        String sql = "SELECT * FROM Patient";
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error happened PatientDao getAll()");
            throw new SQLException();
        }
    }

    @Override
    public boolean insert(Patient patient) throws SQLException {
        Database database = Database.getInstance();
        String sql = "INSERT INTO Patient(patient_name, email, phone_number, owed_balance, total_payed_balance) VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, patient.personInfo().name());
            preparedStatement.setString(2, patient.personInfo().email());
            preparedStatement.setString(3, patient.personInfo().phoneNumber());
            preparedStatement.setFloat(4, 0.0f);
            preparedStatement.setFloat(5, 0.0f);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Patient object) {
        return false;
    }

    @Override
    public Patient getById(int id) {
        return null;
    }

    @Override
    public ResultSet getRowCount() throws SQLException {
        return null;
    }

    public static ConcretePatientDao getInstance() {
        return INSTANCE;
    }
}
