package authentication;

import models.Doctor;
import models.EmployeeInfo;

import java.sql.SQLException;

public interface Authentication {
    int authenticateEmployee(EmployeeInfo employeeInfo) throws SQLException;

    int authenticateDoctor(Doctor doctor) throws SQLException;
}
