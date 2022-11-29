package controllers;

import authentication.Auth;
import models.LoginInfo;
import views.interfaces.InfoView;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SignInController {
    private InfoView<LoginInfo> signInView;

    public SignInController(InfoView<LoginInfo> signInView) {
        this.signInView = signInView;

        signInView.display();
        signInView.registerListeners(Listeners());
    }

    private List<ActionListener> Listeners() {
        return List.of(singInBtnListener());
    }

    private ActionListener singInBtnListener() {
        return actionEvent -> {
            Optional<LoginInfo> optionalEmpInfo = getEmployeeInfo();

            if (optionalEmpInfo.isPresent()) {
                LoginInfo loginInfo = optionalEmpInfo.get();
                try {
                    switch (Auth.getInstance().authenticateEmployee(loginInfo)) {
                        case 1 -> {
                            new HomeController();
                            clean();
                        }
                        case 2 -> {
                            new DoctorController();
                            clean();
                        }
                        case 0 -> signInView.displayMessage("Wrong password or username, Please try again");
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    signInView.displayMessage("Error Happened, please check your connection");
                }
            }
        };
    }

    private Optional<LoginInfo> getEmployeeInfo() {
        Optional<LoginInfo> employeeInfo = Optional.empty();
        try {
            employeeInfo = Optional.of(signInView.getInfo());
        } catch (IllegalArgumentException e) {
            signInView.displayMessage("Please Enter username and password");
        }
        return employeeInfo;
    }

    private void clean() {
        signInView.getJFrame().dispose();
        signInView = null;
    }
}
