package controllers;

import authentication.Auth;
import database.dao.ConcreteDoctorDao;
import models.Doctor;
import org.jetbrains.annotations.Nullable;
import views.interfaces.InfoView;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class RegisterDoctorController {
    private InfoView<Doctor> doctorInfoView;

    public RegisterDoctorController(InfoView<Doctor> doctorInfoView) {
        this.doctorInfoView = doctorInfoView;

        doctorInfoView.display();
        doctorInfoView.registerListeners(listeners());
    }

    private List<ActionListener> listeners() {
        return List.of(registerDoctorBtnListeners());
    }

    private ActionListener registerDoctorBtnListeners() {
        return actionEvent -> {
            Doctor doctor = getDoctorInfo();
            if (doctor != null) {
                try {
                    switch (Auth.getInstance().authenticateDoctor(doctor)) {
                        case 0:
                            doctorInfoView.displayMessage("already registered");
                            break;

                        case 1:
                            doctorInfoView.displayMessage("choose another username");
                            break;

                        case -1:
                            doctorInfoView.displayMessage("Error Happened");
                            break;

                        case 2:
                            if (ConcreteDoctorDao.getInstance().insert(doctor) != 0) {
                                doctorInfoView.displayMessage("registration completed");
                                clean();
                            } else
                                doctorInfoView.displayMessage("Error Happened");
                            break;
                    }
                } catch (SQLException e) {
                    doctorInfoView.displayMessage("Error Happened");
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private @Nullable Doctor getDoctorInfo() {
        Doctor doctor = null;
        try {
            doctor = doctorInfoView.getInfo();
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid info");
        }
        return doctor;
    }

    private void clean() {
        doctorInfoView.getJFrame().dispose();
        doctorInfoView = null;

    }
}
