package controllers;


import models.Patient;
import models.dao.ConcretePatientDao;
import views.InfoView;
import org.jetbrains.annotations.Nullable;
import views.HomeView;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import static models.utilities.ValidationMethods.isValidPatient;

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
            Patient patient = getPatient();
            if (patient != null) {
                try {
                    if (isValidPatient(patient)) {
                        ConcretePatientDao.getInstance().insert(patient);
                        registerPatientView.displayMessage("Patient Inserted");
                        clean();
                    } else {
                        registerPatientView.displayMessage("Patient already registered");
                        clean();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private @Nullable Patient getPatient() {
        Patient patient = null;
        try {
            patient = registerPatientView.getInfo();
        } catch (IllegalArgumentException e) {
            System.err.println("invalid info");
        }
        return patient;
    }

    private void clean() {
        registerPatientView.getJFrame().dispose();
        registerPatientView = null;
        HomeView.getInstance().setVisible(true);
    }
}
