package database.dao;

import database.Database;
import models.Patient;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ConcretePatientDao implements PatientDao {

    private static final ConcretePatientDao INSTANCE = new ConcretePatientDao();
    private static final Connection db_con = Database.getInstance().getConnection();

    private ConcretePatientDao() {

    }

    @Override
    public Collection<Patient> getAll() throws SQLException {
        String sql = "SELECT * FROM Patient";
        Collection<Patient> patients = new ArrayList<>();

        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Patient patient = Patient.getBuilder().
                        withPatientID(result.getInt("ID")).
                        withName(result.getString("patient_name")).
                        withEmail(result.getString("email")).
                        withPhoneNumber(result.getString("phone_number")).
                        withOwedBalance(BigDecimal.valueOf(result.getFloat("owed_balance"))).
                        withPayedBalance(BigDecimal.valueOf(result.getFloat("total_payed_balance"))).
                        withVisitID(result.getInt("visit_ID")).
                        withAppointmentID(result.getInt("app_ID")).
                        build();
                patients.add(patient);
            }
            return patients;
        } catch (SQLException ex) {
            System.err.println("Error happened PatientDao getAll()");
            throw new SQLException();
        }
    }

    @Override
    public Integer insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patient(patient_name, email, phone_number, owed_balance, total_payed_balance) VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
    public Integer delete(int id) throws SQLException {
        String sql = "DELETE FROM Patient WHERE ID =" + id;
        try (PreparedStatement statement = db_con.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Integer update(Patient object) throws SQLException {
        return 0;
    }

    @Override
    public Patient getById(int id) throws SQLException {
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
