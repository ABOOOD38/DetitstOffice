package database.dao;

import database.Database;
import models.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ConcreteScheduleDao implements ScheduleDao {
    private static final ConcreteScheduleDao INSTANCE = new ConcreteScheduleDao();
    private final static Connection db_con = Database.getInstance().getConnection();

    private ConcreteScheduleDao() {
    }

    @Override
    public Collection<Schedule> getAll() throws SQLException {
        String sql = "SELECT ID, start_at, end_at FROM Schedule";

        Collection<Schedule> schedules = new ArrayList<>();

        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql)) {
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Schedule schedule = Schedule.getBuilder().
                        withID(result.getInt("ID")).
                        withStartAt(LocalDate.parse(result.getDate("start_at").toString())).
                        withEndAt(LocalDate.parse(result.getDate("due_to").toString())).
                        build();
                schedules.add(schedule);
            }
            return schedules;
        } catch (SQLException e) {
            System.err.println("getAll\n" + e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Integer delete(int id) throws SQLException {
        String sql = "DELETE FROM Schedule WHERE ID =" + id;
        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql)) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("delete(id)\n" + e.getMessage());
            throw new SQLException();
        }
    }

    // 0 if not inserted
    @Override
    public Integer insert(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO Schedule(start_at, end_at, days, start_time, end_time) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = db_con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(schedule.startAt()));
            preparedStatement.setDate(2, Date.valueOf(schedule.endAt()));
            preparedStatement.setString(3, schedule.days());
            preparedStatement.setTime(4, schedule.startTime());
            preparedStatement.setTime(5, schedule.endTime());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) {
            System.err.println("insert(Schedule)\n" + e.getMessage());
            throw new SQLException();
        }
        return 0;
    }

    @Override
    public Integer update(Schedule schedule) throws SQLException {
        String sql = "UPDATE Schedule SET start_at = ?, end_at = ?, days = ?, start_time = ?, end_time = ? WHERE ID = ?";
        try (PreparedStatement statement = db_con.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(schedule.startAt()));
            statement.setDate(2, Date.valueOf(schedule.endAt()));
            statement.setString(3, schedule.days());
            statement.setTime(4, schedule.startTime());
            statement.setTime(5, schedule.endTime());
            statement.setInt(6, schedule.id());
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Schedule getById(int id) throws SQLException {
        return null;
    }

    @Override
    public Integer getRowCount() throws SQLException {
        return null;
    }

    public static ConcreteScheduleDao getInstance() {
        return INSTANCE;
    }
}
