package database.dao;

import database.Database;
import models.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConcreteDoctorDao implements DoctorDao {

    private final static ConcreteDoctorDao INSTANCE = new ConcreteDoctorDao();

    private ConcreteDoctorDao() {

    }

    @Override
    public ResultSet getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean insert(Doctor doctor) throws SQLException {
        String insertDoctorSql = "insert into Doctor (doctor_name, email, phone_number,login_id) VALUES (?,?,?,?)";

        try (ResultSet generatedKeys = ConcreteEmployeeDao.getInstance().insert(doctor.employee())) {
            if (generatedKeys != null) {
                try (PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(insertDoctorSql)) {
                    statement.setString(1, doctor.personInfo().name());
                    statement.setString(2, doctor.personInfo().email());
                    statement.setString(3, doctor.personInfo().phoneNumber());
                    if (generatedKeys.next()) {
                        statement.setInt(4, generatedKeys.getInt(1));
                        System.out.println("key");
                    }
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("not inserted");
            throw new SQLException();
        }
        return true;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Doctor object) throws SQLException {
        return false;
    }

    @Override
    public Doctor getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getRowCount() throws SQLException {
        return null;
    }

    @Override
    public ResultSet getDoctorJoinLogin() throws SQLException {
        String sql = "select doctor_name, email, phone_number, user_name, password FROM Doctor INNER JOIN Login ON Doctor.login_id = Login.ID";

        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error happened ConcreteDoctorDao getDoctorJoinLogin()");
            throw new SQLException();
        }
    }

    @Override
    public ResultSet getDoctorJoinSchedule() throws SQLException {
        String sql = "SELECT schedule_id, Doctor.ID, doctor_name, email, phone_number, start_at, due_to FROM Doctor LEFT OUTER JOIN  Schedule ON Doctor.schedule_id = Schedule.ID";
        try {
            PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("getSchedule");
            throw new SQLException();
        }
    }

    public static ConcreteDoctorDao getInstance() {
        return INSTANCE;
    }
}
