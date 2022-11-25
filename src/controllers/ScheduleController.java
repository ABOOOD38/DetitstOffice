package controllers;

import database.dao.ConcreteDoctorDao;
import database.dao.ConcreteScheduleDao;
import models.Schedule;
import models.ScheduleTable;
import org.jetbrains.annotations.Nullable;
import views.interfaces.TableInfoView;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import static utilities.ValidationMethods.isValidDate;

public class ScheduleController {
    private final TableInfoView<ScheduleTable> scheduleView;

    public ScheduleController(TableInfoView<ScheduleTable> scheduleView) {
        this.scheduleView = scheduleView;

        setScheduleTable();
        scheduleView.display();
        scheduleView.registerListeners(listeners());
    }

    private List<ActionListener> listeners() {
        return List.of(add_SListener(), remove_SListener(), update_SListener());
    }

    private ActionListener add_SListener() {
        return actionEvent -> {
            Schedule schedule = getSelectedSchedule();
            if (schedule != null) {
                try {
                    int fk = ConcreteScheduleDao.getInstance().insert(schedule);
                    ConcreteDoctorDao.getInstance().update(schedule.id(), fk);
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private ActionListener remove_SListener() {
        return actionEvent -> {
            Schedule schedule = getSelectedSchedule();
            if (schedule != null) {
                try {
                    int fk = ConcreteDoctorDao.getInstance().getById(schedule.id()).schedule().id();
                    System.err.println(ConcreteScheduleDao.getInstance().delete(fk));
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }

        };
    }

    private ActionListener update_SListener() {
        return actionEvent -> {
            System.out.println("Update");
        };
    }

    private @Nullable Schedule getSelectedSchedule() {
        Schedule schedule = scheduleView.getSelectedSchedule();
        if (schedule != null)
            if (schedule.id() != 0)
                if (isValidDate(schedule))
                    return schedule;
                else
                    scheduleView.displayMessage("Must be at lest one week between start_date and end_date ");
            else
                scheduleView.displayMessage("Please select doctor_id from the table");
        return schedule;
    }

    private void setScheduleTable() {
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
            scheduleView.setInfo(new ScheduleTable(colsName, rows));

        } catch (SQLException ex) {
            scheduleView.displayMessage("Error happened please try again");
            System.err.println("Error in setSchedule()");
            System.err.println(ex.getMessage());
        }
    }

    private String[] getScheduleColNames(ResultSet scheduleSet) throws SQLException {
        try {
            ResultSetMetaData resultSetMetaData = scheduleSet.getMetaData();
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

    private int getScheduleColsNum(ResultSet scheduleSet) throws SQLException {
        try {
            ResultSetMetaData resultSetMetaData = scheduleSet.getMetaData();
            return resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            System.err.println("Error in getScheduleColsNum(ResultSet scheduleSet)");
            throw new SQLException();
        }
    }

    private int getScheduleRowsCount() throws SQLException {
        try {
            ResultSet resultSet = ConcreteScheduleDao.getInstance().getRowCount();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error in getScheduleRowsCount()");
            throw new SQLException();
        }
    }
}
