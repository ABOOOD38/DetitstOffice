package database.dao;

import database.Database;
import models.Schedule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConcreteScheduleDao implements Dao<Schedule> {
    private static final ConcreteScheduleDao INSTANCE = new ConcreteScheduleDao();

    private ConcreteScheduleDao() {

    }

    @Override
    public ResultSet getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
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
            System.err.println("getScheduleRowsNum()");
            throw new RuntimeException();
        }
    }

    public static ConcreteScheduleDao getInstance() {
        return INSTANCE;
    }
}
