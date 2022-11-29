package database.dao;

import database.Database;
import models.LoginInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ConcreteEmployeeDao implements Dao<LoginInfo> {

    private static final ConcreteEmployeeDao INSTANCE = new ConcreteEmployeeDao();
    private static final Connection db_con = Database.getInstance().getConnection();

    private ConcreteEmployeeDao() {

    }

    @Override
    public Collection<LoginInfo> getAll() throws SQLException {
        String sql = "SELECT user_name, password, role_id FROM Login";

        Collection<LoginInfo> loginInfos = new ArrayList<>();

        try (PreparedStatement statement = db_con.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                LoginInfo empInfo = LoginInfo.getBuilder().
                        withUserName(result.getString("user_name")).
                        withPassword(result.getString("password")).
                        withRoleID(result.getInt("role_id")).
                        build();
                loginInfos.add(empInfo);
            }
            return loginInfos;
        } catch (SQLException e) {
            System.err.println("Error happened getLoginsInfo()");
            throw new SQLException();
        }
    }

    @Override
    public Integer insert(LoginInfo loginInfo) throws SQLException {
        String loginSql = "insert into Login (user_name, password, role_id) VALUES (?,?,?)";
        try {
            PreparedStatement statement = db_con.prepareStatement(loginSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, loginInfo.userName());
            statement.setString(2, loginInfo.password());
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
        String sql = "DELETE FROM Login WHERE ID =" + id;
        try (PreparedStatement statement = db_con.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Integer update(LoginInfo object) throws SQLException {
        return 0;
    }

    @Override
    public LoginInfo getById(int id) throws SQLException {
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
