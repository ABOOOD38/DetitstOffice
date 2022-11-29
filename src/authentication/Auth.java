package authentication;

import database.dao.ConcreteDoctorDao;
import database.dao.ConcreteEmployeeDao;
import models.Doctor;
import models.LoginInfo;

import java.sql.SQLException;
import java.util.Collection;


public class Auth implements Authentication {
    private static final Auth INSTANCE = new Auth();

    private Auth() {

    }

    // -1 error sql, 0 not found
    @Override
    public int authenticateEmployee(LoginInfo loginInfo) throws SQLException {
        Collection<LoginInfo> loginInfos = ConcreteEmployeeDao.getInstance().getAll();
        if (loginInfos == null)
            throw new SQLException();

        for (LoginInfo empInfo : loginInfos)
            if (loginInfo.equals(empInfo)) {
                return empInfo.roleID();
            }
        return 0;
    }

    // 0 already registered, 1 same username, 2 no found
    @Override
    public int authenticateDoctor(Doctor doctor) throws SQLException {
        Collection<Doctor> doctors = ConcreteDoctorDao.getInstance().getDoctorJoinLogin();
        if (doctors == null) throw new SQLException();

        for (Doctor _doctor : doctors) {
            if (doctor.loginInfo().equalsUserName(_doctor.loginInfo()))
                if (doctor.personalInfo().equalsEmailOrPhoneNum(_doctor.personalInfo()))
                    return 0;
                else
                    return 1;
        }
        return 2;
    }

    public static Auth getInstance() {
        return INSTANCE;
    }
}
