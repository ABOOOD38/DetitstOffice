package database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface Dao<T> {
    Collection<T> getAll() throws SQLException;

    Integer delete(int id) throws SQLException;

    Integer insert(T object) throws SQLException;

    Integer update(T object) throws SQLException;

    T getById(int id) throws SQLException;

    Integer getRowCount() throws SQLException;
}
