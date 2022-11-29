package database.dao;

import database.Database;
import models.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ConcreteDoctorDao implements DoctorDao<Doctor> {

    private final static ConcreteDoctorDao INSTANCE = new ConcreteDoctorDao();
    private final static Connection db_con = Database.getInstance().getConnection();

    private ConcreteDoctorDao() {

    }

    @Override
    public Collection<Doctor> getAll() throws SQLException {
        return null;
    }

    @Override
    public Integer insert(Doctor doctor) throws SQLException {
        String insertDoctorSql = "insert into Doctor (doctor_name, email, phone_number,login_id) VALUES (?,?,?,?)";

        Integer key = ConcreteEmployeeDao.getInstance().insert(doctor.loginInfo());
        try (PreparedStatement statement = db_con.prepareStatement(insertDoctorSql)) {
            statement.setString(1, doctor.personalInfo().name());
            statement.setString(2, doctor.personalInfo().email());
            statement.setString(3, doctor.personalInfo().phoneNumber());
            if (key != 0) {
                statement.setInt(4, key);
                System.out.println(key);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("not inserted");
            throw new SQLException();
        }
    }

    @Override
    public Integer delete(int id) throws SQLException {
        String sql = "DELETE FROM Doctor WHERE doctor_id =" + id;
        try (PreparedStatement statement = db_con.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Integer update(Doctor object) throws SQLException {
        return 0;
    }

    @Override
    public Doctor getById(int id) throws SQLException {
        String sql = "SELECT doctor_name, email, phone_number, schedule_id, login_id FROM Doctor WHERE doctor_id =" + id;
        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Doctor.getBuilder().
                    withName(resultSet.getString("doctor_name")).
                    withEmail(resultSet.getString("email")).
                    withPhoneNumber(resultSet.getString("phone_number")).
                    withScheduleID(resultSet.getInt("schedule_id"))
                    .build();
        } catch (SQLException e) {
            System.err.println("getById(int id)\n" + e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Integer getRowCount() throws SQLException {
        return null;
    }

    @Override
    public Collection<Doctor> getDoctorJoinLogin() throws SQLException {
        String sql = "select doctor_name, email, phone_number, user_name, password FROM Doctor INNER JOIN Login ON Doctor.login_id = Login.ID";
        Collection<Doctor> doctors = new ArrayList<>();

        try (PreparedStatement statement = db_con.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Doctor doctor = Doctor.getBuilder().
                        withName(result.getString("doctor_name")).
                        withEmail(result.getString("email")).
                        withPhoneNumber(result.getString("phone_number")).
                        withUserName(result.getString("user_name")).
                        withPassword(result.getString("password")).
                        build();

                doctors.add(doctor);
            }
            return doctors;
        } catch (SQLException e) {
            System.err.println("Error happened ConcreteDoctorDao getDoctorJoinLogin()");
            throw new SQLException();
        }
    }

    @Override
    public Collection<Doctor> getDoctorJoinSchedule() throws SQLException {
        String sql = "SELECT Doctor.doctor_id, doctor_name, email, phone_number, schedule_id, start_at, due_to FROM Doctor LEFT OUTER JOIN  Schedule ON Doctor.schedule_id = Schedule.ID";

        Collection<Doctor> doctors = new ArrayList<>();
        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Doctor doctor = Doctor.getBuilder().
                        withID(result.getInt("doctor_id")).
                        withName(result.getString("doctor_name")).
                        withEmail(result.getString("email")).
                        withPhoneNumber(result.getString("phone_number")).
                        withScheduleID(result.getInt("schedule_id")).
                        withStartAt(LocalDate.parse(result.getDate("start_at").toString())).
                        withEndAt(LocalDate.parse(result.getDate("due_to").toString())).
                        build();

                doctors.add(doctor);
            }
            return doctors;
        } catch (SQLException e) {
            System.err.println("getSchedule");
            throw new SQLException();
        }
    }

    @Override
    public Integer update(Integer id, Integer fk) throws SQLException {
        String sql = "UPDATE Doctor SET schedule_id = " + fk + " WHERE doctor_id = " + id;
        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("update(Integer id, Integer fk)\n" + e.getMessage());
            throw new SQLException();
        }
    }

    public static ConcreteDoctorDao getInstance() {
        return INSTANCE;
    }
}
