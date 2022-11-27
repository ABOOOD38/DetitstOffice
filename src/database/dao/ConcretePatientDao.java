package database.dao;

import database.Database;
import models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class ConcretePatientDao implements PatientDao {

    private static final ConcretePatientDao INSTANCE = new ConcretePatientDao();

    private ConcretePatientDao() {

    }

    @Override
    public Collection<Patient> getAll() throws SQLException {
        Database database = Database.getInstance();
        String sql = "SELECT * FROM Patient";
        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql);
            //return preparedStatement.executeQuery();
            return null;
            // TODO: 11/26/22  
        } catch (SQLException ex) {
            System.err.println("Error happened PatientDao getAll()");
            throw new SQLException();
        }
    }

    @Override
    public Integer insert(Patient patient) throws SQLException {
        Database database = Database.getInstance();
        String sql = "INSERT INTO Patient(patient_name, email, phone_number, owed_balance, total_payed_balance) VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, patient.personalInfo().name());
            preparedStatement.setString(2, patient.personalInfo().email());
            preparedStatement.setString(3, patient.personalInfo().phoneNumber());
            preparedStatement.setFloat(4, 0.0f);
            preparedStatement.setFloat(5, 0.0f);
            preparedStatement.executeUpdate();
            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next())
                return key.getInt(1);
        }
        return 0;
    }

    @Override
    public Integer delete(int id) {
        return 0;
    }

    @Override
    public Integer update(Patient object) {
        return 0;
    }

    @Override
    public Patient getById(int id) {
        return null;
    }

    @Override
    public Integer getRowCount() throws SQLException {
        return null;
    }

    public static ConcretePatientDao getInstance() {
        return INSTANCE;
    }
}
