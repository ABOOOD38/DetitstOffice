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

public class ScheduleController {// TODO: 11/29/22
    private final TableInfoView<TableView> scheduleView;
    private static final ConcreteScheduleDao scheduleDB = ConcreteScheduleDao.getInstance();
    private static final ConcreteDoctorDao doctorDB = ConcreteDoctorDao.getInstance();

    public ScheduleController(TableInfoView<TableView> scheduleView) {
        this.scheduleView = scheduleView;

        setScheduleTable();
        scheduleView.display();
        scheduleView.registerListeners(listeners());
    }

    private List<ActionListener> listeners() {
        return List.of(removeListener(), updateListener());
    }

    private ActionListener removeListener() {
        return actionEvent -> {
            int doctorID = getSelectedDoctorId();
            if (doctorID != -1) {
                try {
                    int doctor_schedule_fk = doctorDB.getById(doctorID).schedule().id();
                    if (doctor_schedule_fk != 0) {
                        if (scheduleDB.delete(doctor_schedule_fk) != 0) {
                            showMessage("removed successfully");
                            setScheduleTable();
                        }
                    } else
                        showMessage("please choose non empty start and end date");
                    scheduleView.resetSelectedID();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private ActionListener updateListener() {
        return actionEvent -> updateOrInsertSchedule();
    }

    private @Nullable Schedule getSelectedSchedule() {
        Schedule schedule = scheduleView.getSelectedSchedule();
        if (schedule != null)
            if (getSelectedDoctorId() != -1)
                if (isValidDate(schedule))
                    return schedule;
                else
                    showMessage("Must be at lest one week between start_date and end_date ");
        return null;
    }

    private void setScheduleTable() {
        try {
            scheduleView.setInfo(DoctorScheduleTableInfo.getInstance().getTableValues());
        } catch (SQLException e) {
            showMessage("Error happened");
            System.err.println(e.getMessage());
        }
    }

    private void insertSchedule(Schedule schedule) throws SQLException {
        int fk = scheduleDB.insert(schedule);
        doctorDB.update(getSelectedDoctorId(), fk);
        showMessage("added successfully");
        scheduleView.resetSelectedID();
        setScheduleTable();
    }

    private void updateSchedule(Schedule schedule, int id) throws SQLException {
        schedule = updateScheduleObjID(schedule, id);
        if (scheduleDB.update(schedule) != 0) {
            setScheduleTable();
            showMessage("Updated successfully");
        }
    }

    private void updateOrInsertSchedule() {
        Schedule schedule = getSelectedSchedule();
        if (schedule != null) {
            int doctorID = getSelectedDoctorId();
            if (doctorID == -1) return;

            try {
                int doctor_schedule_fk = doctorDB.getById(doctorID).schedule().id();
                if (doctor_schedule_fk == 0)
                    insertSchedule(schedule);
                else
                    updateSchedule(schedule, doctor_schedule_fk);
            } catch (SQLException e) {
                showMessage("Error happened");
                System.err.println(e.getMessage());
            }
        }
    }

    private int getSelectedDoctorId() {
        int id = scheduleView.getSelectedID();
        if (id != 0) {
            return id;
        }
        showMessage("Please select ID (doctor id) from the table");
        return -1;
    }

    private Schedule updateScheduleObjID(Schedule schedule, int id) {
        return Schedule.getBuilder().
                withStartAt(schedule.startAt()).
                withEndAt(schedule.endAt()).
                withID(id).build();
    }

    private void showMessage(String message) {
        scheduleView.displayMessage(message);
    }
}