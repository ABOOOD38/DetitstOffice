package controllers;

import authentication.Auth;
import database.dao.ConcreteDoctorDao;
import models.Doctor;
import views.HomeView;
import views.interfaces.InfoView;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
            Optional<Doctor> optionalDoctor = getDoctorInfo();
            if (optionalDoctor.isPresent()) {
                Doctor doctor = optionalDoctor.get();
                try {
                    switch (Auth.getInstance().authenticateDoctor(doctor)) {
                        case 0:
                            doctorInfoView.displayMessage("already registered");
                            break;

                        case 1:
                            doctorInfoView.displayMessage("choose another username");
                            break;

                        case 2:
                            if (ConcreteDoctorDao.getInstance().insert(doctor) == 1) {
                                doctorInfoView.displayMessage("registration completed");
                                clean();
                            } else
                                doctorInfoView.displayMessage("Error Happened when inserting doctor");
                            break;
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private Optional<Doctor> getDoctorInfo() {
        Optional<Doctor> doctor = Optional.empty();
        try {
            doctor = Optional.of(doctorInfoView.getInfo());
        } catch (IllegalArgumentException ex) {
            System.err.println("errors: caught on the view");
        }
        return doctor;
    }

    private void clean() {
        doctorInfoView.getJFrame().dispose();
        doctorInfoView = null;
        HomeView.getInstance().setVisible(true);
    }
}
