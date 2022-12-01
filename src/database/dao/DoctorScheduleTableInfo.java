package database.dao;

import database.Database;
import models.Schedule;
import models.TableView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DoctorScheduleTableInfo implements TableInfo<Schedule> {
    private static final DoctorScheduleTableInfo INSTANCE = new DoctorScheduleTableInfo();

    private DoctorScheduleTableInfo() {

    }

    private String[] getColsNames(ResultSet resultSet) throws SQLException {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            String[] cols = new String[resultSetMetaData.getColumnCount()];
            for (int i = 0; i < cols.length; ++i) {
                cols[i] = resultSetMetaData.getColumnName(i + 1);
            }
            return cols;
        } catch (SQLException e) {
            System.err.println("error in getScheduleColNames(ResultSet scheduleSet)");
            throw new SQLException();
        }
    }

    private Integer getColsCount(ResultSet resultSet) throws SQLException {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            return resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            System.err.println("Error in getColsCount(ResultSet resultSet)");
            throw new SQLException();
        }
    }

    @Override
    public Integer getRowsCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Doctor LEFT OUTER JOIN  Schedule ON Doctor.schedule_id = Schedule.ID";
        try (PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            if (result.next())
                return result.getInt(1);
            return -1;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public TableView getTableValues() throws SQLException {
        String sql = "SELECT Doctor.ID, name, email, phone_number, schedule_id, start_at, end_at FROM Doctor LEFT OUTER JOIN  Schedule ON Doctor.schedule_id = Schedule.ID";
        try (PreparedStatement statement = Database.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            String[] colsNames = getColsNames(result);
            int rowsCount = getRowsCount();
            int colsCount = getColsCount(result);

            System.out.println(rowsCount + " " + colsCount);

            String[][] rowsValues = new String[rowsCount][colsCount];

            int row = 0;
            while (result.next()) {
                for (int col = 0; col < colsCount; ++col) {
                    Object info = result.getObject(col + 1);
                    if (info != null)
                        rowsValues[row][col] = info.toString();
                    else
                        rowsValues[row][col] = "Empty";
                }
                ++row;
            }
            return new TableView(colsNames, rowsValues);
        }
    }

    public static DoctorScheduleTableInfo getInstance() {
        return INSTANCE;
    }
}
