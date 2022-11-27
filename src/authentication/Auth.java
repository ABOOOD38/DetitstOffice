package authentication;

import database.dao.ConcreteDoctorDao;
import database.dao.ConcreteEmployeeDao;
import models.Doctor;
import models.EmployeeInfo;

import java.sql.SQLException;
import java.util.Collection;


public class Auth implements Authentication {
    private static final Auth INSTANCE = new Auth();

    private Auth() {

    }

    // -1 error sql, 0 not found
    @Override
    public int authenticateEmployee(EmployeeInfo employeeInfo) throws SQLException {
        Collection<EmployeeInfo> employeeInfos = ConcreteEmployeeDao.getInstance().getAll();
        if (employeeInfos == null) return -1;

        for (EmployeeInfo empInfo : employeeInfos)
            if (employeeInfo.equals(empInfo)) {
                return empInfo.roleID();
            }
        return 0;
    }

    // 0 already registered, 1 same username, 2 no found, -1 error
    @Override
    public int authenticateDoctor(Doctor doctor) throws SQLException {
        Collection<Doctor> doctors = ConcreteDoctorDao.getInstance().getDoctorJoinLogin();
        if (doctors == null) return 3;

        for (Doctor _doctor : doctors)
            if (doctor.employeeInfo().equalsUserName(_doctor.employeeInfo()))
                if (doctor.personalInfo().equalsEmailOrPhoneNum(_doctor.personalInfo())) return 0;
                else return 1;
        return -1;
    }

    public static Auth getInstance() {
        return INSTANCE;
    }
}
