package database.dao;

import database.Database;
import models.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConcreteEmployeeDao implements EmployeeDao {

    private static final ConcreteEmployeeDao INSTANCE = new ConcreteEmployeeDao();

    private ConcreteEmployeeDao() {

    }

    @Override
    public ResultSet getAll() throws SQLException {
        String sql = "select user_name, password, role_id from Login";

        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error happened getLoginsInfo()");
            throw new SQLException();
        }
    }

    @Override
    public ResultSet insert(Employee employee) throws SQLException {
        String loginSql = "insert into Login (user_name, password, role_id) VALUES (?,?,?)";
        ResultSet generatedKeys;

        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(loginSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.userName());
            statement.setString(2, employee.password());
            statement.setInt(3, 2);
            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
        } catch (SQLException e) {
            System.err.println("Creating user failed, no ID obtained.");
            throw new SQLException();
        }
        return generatedKeys;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Employee object) throws SQLException {
        return false;
    }

    @Override
    public Employee getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ResultSet getRowCount() throws SQLException {
        return null;
    }

    public static ConcreteEmployeeDao getInstance() {
        return INSTANCE;
    }
}
