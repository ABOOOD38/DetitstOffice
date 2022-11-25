package database.dao;

import database.Database;
import models.Schedule;

import java.sql.*;

public class ConcreteScheduleDao implements Dao<Schedule> {
    private static final ConcreteScheduleDao INSTANCE = new ConcreteScheduleDao();

    private ConcreteScheduleDao() {
    }

    @Override
    public ResultSet getAll() throws SQLException {
        String sql = "SELECT * FROM Schedule";

        try {
            PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("getAll\n" + e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Schedule WHERE ID = " + id;
        try (PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("delete(id)\n" + e.getMessage());
            throw new SQLException();
        }
    }

    // 0 if not inserted
    @Override
    public Integer insert(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO Schedule(start_at, due_to) VALUES (?,?)";
        try (PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, Date.valueOf(schedule.startAt()));
            preparedStatement.setDate(2, Date.valueOf(schedule.endAt()));
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
    public boolean update(Schedule object) throws SQLException {
        return false;
    }

    @Override
    public Schedule getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getRowCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Schedule RIGHT OUTER JOIN Doctor D on Schedule.ID = D.schedule_id";
        try {
            PreparedStatement preparedStatement = Database.getInstance().getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("getScheduleRowsNum()\n" + e.getMessage());
            throw new SQLException();
        }
    }

    public static ConcreteScheduleDao getInstance() {
        return INSTANCE;
    }
}
