package controllers;


import database.dao.ConcretePatientDao;
import models.Patient;
import views.HomeView;
import views.interfaces.InfoView;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static utilities.ValidationMethods.isValidPatient;

public class RegisterPatientController {
    private InfoView<Patient> registerPatientView;

    public RegisterPatientController(InfoView<Patient> registerPatientView) {
        this.registerPatientView = registerPatientView;

        registerPatientView.display();
        registerPatientView.registerListeners(listeners());
    }

    private List<ActionListener> listeners() {
        return List.of(registerBtnListener());
    }

    private ActionListener registerBtnListener() {
        return actionEvent -> {
            Optional<Patient> optionalPatient = getPatient();
            if (optionalPatient.isPresent()) {
                Patient patient = optionalPatient.get();
                try {
                    if (isValidPatient(patient)) {
                        if (ConcretePatientDao.getInstance().insert(patient) != 0) {
                            registerPatientView.displayMessage("Patient Inserted");
                            clean();
                        }
                    } else {
                        registerPatientView.displayMessage("Patient already registered");
                        clean();
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }

    private Optional<Patient> getPatient() {
        Optional<Patient> patient = Optional.empty();
        try {
            patient = Optional.of(registerPatientView.getInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("errors: caught on the view");
        }
        return patient;
    }

    private void clean() {
        registerPatientView.getJFrame().dispose();
        registerPatientView = null;
        HomeView.getInstance().setVisible(true);
    }
}
