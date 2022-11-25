package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Dao<T> {
    ResultSet getAll() throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(T object) throws SQLException;

    T getById(int id) throws SQLException;

    ResultSet getRowCount() throws SQLException;
}
