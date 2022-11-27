package database.dao;

import models.TableView;

import java.sql.SQLException;

public interface TableInfo<T> {

    Integer getRowsCount() throws SQLException;

    TableView getTableValues() throws SQLException;
}
