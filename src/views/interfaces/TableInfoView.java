package views.interfaces;

import models.Schedule;

public interface TableInfoView<T> extends InfoView<T> {
    void setInfo(T info);
    Schedule getSelectedSchedule();

    Integer getSelectedID();
    void resetScheduleID();
}
