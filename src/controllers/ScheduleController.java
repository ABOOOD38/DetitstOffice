package controllers;

import database.dao.ConcreteDoctorDao;
import database.dao.ConcreteScheduleDao;
import database.dao.DoctorScheduleTableInfo;
import models.Schedule;
import models.TableView;
import org.jetbrains.annotations.Nullable;
import views.interfaces.TableInfoView;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import static utilities.ValidationMethods.isValidDate;

public class ScheduleController {
    private final TableInfoView<TableView> scheduleView;

    public ScheduleController(TableInfoView<TableView> scheduleView) {
        this.scheduleView = scheduleView;

        setScheduleTable();
        scheduleView.display();
        scheduleView.registerListeners(listeners());
    }

    private List<ActionListener> listeners() {
        return List.of(addListener(), removeListener(), updateListener());
    }

    private ActionListener addListener() {
        return actionEvent -> {
            Schedule schedule = getSelectedSchedule();
            if (schedule != null && schedule.id() != 0) {
                try {
                    int fk = ConcreteScheduleDao.getInstance().insert(schedule);
                    ConcreteDoctorDao.getInstance().update(schedule.id(), fk);
                    scheduleView.displayMessage("added successfully");
                    scheduleView.resetScheduleID();
                    setScheduleTable();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private ActionListener removeListener() {
        return actionEvent -> {
            Integer scheduleID = scheduleView.getSelectedID();
            if (scheduleID != 0) {
                try {
                    int fk = ConcreteDoctorDao.getInstance().getById(scheduleID).schedule().id();
                    int ffk = ConcreteScheduleDao.getInstance().delete(fk);
                    if (ffk != 0) {
                        scheduleView.displayMessage("removed successfully");
                        setScheduleTable();
                    } else
                        scheduleView.displayMessage("please choose non empty start and end date");

                    scheduleView.resetScheduleID();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private ActionListener updateListener() {
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
        return null;
    }

    private void setScheduleTable() {
        try {
            scheduleView.setInfo(new DoctorScheduleTableInfo().getTableValues());
        } catch (SQLException e) {
            scheduleView.displayMessage("Error happened");
            System.err.println(e.getMessage());
        }
    }
}