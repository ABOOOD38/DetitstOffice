package controllers;

import models.Schedule;
import database.dao.ConcreteDoctorDao;
import database.dao.ConcreteScheduleDao;
import views.interfaces.TableInfoView;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ScheduleController {
    private final TableInfoView<Schedule> scheduleView;

    public ScheduleController(TableInfoView<Schedule> scheduleView) {
        this.scheduleView = scheduleView;

        setSchedule();
        scheduleView.display();
        scheduleView.registerListeners(listeners());
    }

    private List<ActionListener> listeners() {
        return List.of(add_SListener(), remove_SListener(), update_SListener());
    }

    private ActionListener add_SListener() {
        return actionEvent -> {
            System.out.println("Add");
        };
    }

    private ActionListener remove_SListener() {
        return actionEvent -> {
            System.out.println("remove");
        };
    }

    private ActionListener update_SListener() {
        return actionEvent -> {
            System.out.println("Update");
        };
    }

    private void setSchedule() {
        try {
            ResultSet scheduleSet = ConcreteDoctorDao.getInstance().getDoctorJoinSchedule();

            String[] colsName = getScheduleColNames(scheduleSet);
            int rowCount = getScheduleRowsCount();
            int colCount = getScheduleColsNum(scheduleSet);

            System.out.println(rowCount + " " + colCount);
            String[][] rows = new String[rowCount][colCount];

            int row = 0;
            while (scheduleSet.next()) {
                for (int col = 0; col < colCount; ++col) {
                    Object info = scheduleSet.getObject(col + 1);
                    if (info != null)
                        rows[row][col] = info.toString();
                    else
                        rows[row][col] = "Empty";
                }
                ++row;
            }
            scheduleView.setInfo(new Schedule(colsName, rows));

        } catch (RuntimeException | SQLException ex) {
            scheduleView.displayMessage("Error happened please try again");
            System.err.println("Error in setSchedule()");
            System.err.println(ex.getMessage());
        }
    }

    private String[] getScheduleColNames(ResultSet scheduleSet) {
        try {
            ResultSetMetaData resultSetMetaData = scheduleSet.getMetaData();
            String[] cols = new String[resultSetMetaData.getColumnCount()];
            for (int i = 0; i < cols.length; ++i) {
                cols[i] = resultSetMetaData.getColumnName(i + 1);
            }
            return cols;
        } catch (SQLException e) {
            System.err.println("error in getScheduleColNames(ResultSet scheduleSet)");
            throw new RuntimeException(e);
        }
    }

    private int getScheduleColsNum(ResultSet scheduleSet) {
        try {
            ResultSetMetaData resultSetMetaData = scheduleSet.getMetaData();
            return resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            System.err.println("Error in getScheduleColsNum(ResultSet scheduleSet)");
            throw new RuntimeException(e);
        }
    }

    private int getScheduleRowsCount() {
        try {
            ResultSet resultSet = ConcreteScheduleDao.getInstance().getRowCount();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error in getScheduleRowsCount()");
            throw new RuntimeException(e);
        }
    }
}
