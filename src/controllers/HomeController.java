package controllers;

import views.View;
import views.*;

import java.awt.event.ActionListener;
import java.util.List;

public class HomeController {
    private final View homeView;

    public HomeController() {
        this.homeView = HomeView.getInstance();

        homeView.display();
        homeView.registerListeners(Listeners());
    }

    private List<ActionListener> Listeners() {
        return List.of(doctorBtnListener(), patientBtnListener(), appointmentsBtnListener(), scheduleBtnListener());
    }

    private ActionListener doctorBtnListener() {
        return actionEvent -> {
            new RegisterDoctorController(new RegisterDoctorView());
            clean();
        };
    }

    private ActionListener patientBtnListener() {
        return actionEvent -> {
            new RegisterPatientController(new RegisterPatientView());
            clean();
        };
    }

    private ActionListener appointmentsBtnListener() {
        return actionEvent -> {
            new PatientAppointmentController();
            clean();
        };
    }

    private ActionListener scheduleBtnListener() {
        return actionEvent -> {
            new ScheduleController(new ScheduleView());
            clean();
        };
    }

    private void clean() {
        homeView.getJFrame().setVisible(false);
    }
}

