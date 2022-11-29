package authentication;

import models.Doctor;
import models.LoginInfo;

import java.sql.SQLException;

public interface Authentication {
    int authenticateEmployee(LoginInfo loginInfo) throws SQLException;

    int authenticateDoctor(Doctor doctor) throws SQLException;
}
