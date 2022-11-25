package database.dao;

import models.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EmployeeDao extends Dao<Employee> {

    ResultSet insert(Employee employee) throws SQLException;
}
