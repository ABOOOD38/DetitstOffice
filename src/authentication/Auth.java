package authentication;

import database.dao.ConcreteDoctorDao;
import database.dao.ConcreteEmployeeDao;
import models.Doctor;
import models.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import static utilities.ValidationMethods.close;

public class Auth implements Authentication {
    private static final Auth INSTANCE = new Auth();

    private Auth() {

    }

    // -1 error sql
    @Override
    public int authenticateEmployee(Employee employee) throws SQLException {
        ResultSet logins = ConcreteEmployeeDao.getInstance().getAll();
        if (logins != null) {
            while (logins.next())
                if (employee.userName().equals(logins.getString("user_name")) && employee.password().equals(logins.getString("password"))) {
                    return logins.getInt("role_id");
                }
        } else
            return -1;

        close(logins);
        return 0;
    }

    // 0 already registered, 1 same username, 2 no found
    @Override
    public int authenticateDoctor(Doctor doctor) throws SQLException {
        ResultSet doctors = ConcreteDoctorDao.getInstance().getDoctorJoinLogin();
        if (doctors != null) {
            while (doctors.next())
                if (doctor.employee().userName().equals(doctors.getString(4)))
                    if (doctor.personInfo().email().equals(doctors.getString(2)) || doctor.personInfo().phoneNumber().equals(doctors.getString(3)))
                        return 0;
                    else
                        return 1;
        } else
            return 3;

        return 2;
    }

    public static Auth getInstance() {
        return INSTANCE;
    }

}
