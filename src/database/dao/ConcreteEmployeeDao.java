package database.dao;

import database.Database;
import models.EmployeeInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class ConcreteEmployeeDao implements Dao<EmployeeInfo> {

    private static final ConcreteEmployeeDao INSTANCE = new ConcreteEmployeeDao();

    private ConcreteEmployeeDao() {

    }

    @Override
    public Collection<EmployeeInfo> getAll() throws SQLException {
        String sql = "SELECT user_name, password, role_id FROM Login";

        Collection<EmployeeInfo> employeeInfos = new ArrayList<>();

        try (PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                EmployeeInfo empInfo = EmployeeInfo.getBuilder().
                        withUserName(result.getString("user_name")).
                        withPassword(result.getString("password")).
                        withRoleID(result.getInt("role_id")).
                        build();
                employeeInfos.add(empInfo);
            }
            return employeeInfos;
        } catch (SQLException e) {
            System.err.println("Error happened getLoginsInfo()");
            throw new SQLException();
        }
    }

    @Override
    public Integer insert(EmployeeInfo employeeInfo) throws SQLException {
        String loginSql = "insert into Login (user_name, password, role_id) VALUES (?,?,?)";
        try {
            PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(loginSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employeeInfo.userName());
            statement.setString(2, employeeInfo.password());
            statement.setInt(3, 2);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next())
                return key.getInt(1);
        } catch (SQLException e) {
            System.err.println("Creating user failed, no ID obtained.");
            throw new SQLException();
        }
        return 0;
    }

    @Override
    public Integer delete(int id) throws SQLException {
        return 0;
    }

    @Override
    public Integer update(EmployeeInfo object) throws SQLException {
        return 0;
    }

    @Override
    public EmployeeInfo getById(int id) throws SQLException {
        return null;
    }

    @Override
    public Integer getRowCount() throws SQLException {
        return null;
    }

    public static ConcreteEmployeeDao getInstance() {
        return INSTANCE;
    }
}
