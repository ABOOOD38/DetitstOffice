package database.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface DoctorDao<T> extends Dao<T> {

    Collection<T> getDoctorJoinLogin() throws SQLException;

    Collection<T> getDoctorJoinSchedule() throws SQLException;

    Integer update(Integer id, Integer fk) throws SQLException;

}
