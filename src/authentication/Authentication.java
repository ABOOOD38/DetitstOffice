package authentication;

import models.Doctor;
import models.Employee;

import java.sql.SQLException;

public interface Authentication {
    int authenticateEmployee(Employee employeeInfo) throws SQLException;

    int authenticateDoctor(Doctor doctor) throws SQLException;
}
