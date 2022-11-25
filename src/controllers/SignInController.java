package controllers;

import models.Auth;
import models.Employee;
import views.InfoView;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SignInController {
    private InfoView<Employee> signInView;

    public SignInController(InfoView<Employee> signInView) {
        this.signInView = signInView;

        signInView.display();
        signInView.registerListeners(Listeners());
    }

    private List<ActionListener> Listeners() {
        return List.of(singInBtnListener());
    }

    private ActionListener singInBtnListener() {
        return actionEvent -> {
            Employee employeeInfo = getEmployeeInfo();

            if (employeeInfo != null) {
                try {
                    switch (Auth.getInstance().authenticateEmployee(employeeInfo)) {
                        case 1 -> {
                            new HomeController();
                            clean();
                        }
                        case 2 -> {
                            new DoctorController();
                            clean();
                        }
                        case -1, 0 -> signInView.displayMessage("Wrong password or username, Please try again");
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    signInView.displayMessage("Some Error Happened, Please try again");
                }
            }
        };
    }

    private @Nullable Employee getEmployeeInfo() {
        Employee employeeInfo = null;
        try {
            employeeInfo = signInView.getInfo();
        } catch (IllegalArgumentException e) {
            System.err.println("empty");
            signInView.displayMessage("Please Enter username and password");
        }
        return employeeInfo;
    }

    private void clean() {
        signInView.getJFrame().dispose();
        signInView = null;
    }
}
