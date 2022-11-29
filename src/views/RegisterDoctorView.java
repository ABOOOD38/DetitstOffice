package views;

import models.Doctor;
import views.interfaces.InfoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static utilities.ValidationMethods.*;

public class RegisterDoctorView extends JFrame implements InfoView<Doctor> {
    // TODO: 11/28/22  
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private final PersonInfoView personInfoView;
    private JTextField userNameTextField;
    private JPasswordField passwordTextField;
    private JButton register;

    public RegisterDoctorView() {
        personInfoView = new PersonInfoView();
    }

    @Override
    public void display() {
        JPanel backGround = new JPanel(new BorderLayout());

        //***********************labels*********************//
        JLabel userName = new JLabel("userName");
        JLabel password = new JLabel("password");
        JPanel empLabels = new JPanel(new GridLayout(2, 1));
        empLabels.add(userName);
        empLabels.add(password);

        //************************textFields*******************//
        JPanel empTextFields = new JPanel(new GridLayout(2, 1));
        userNameTextField = new JTextField(15);
        passwordTextField = new JPasswordField(15);
        empTextFields.add(userNameTextField);
        empTextFields.add(passwordTextField);

        //**********************empInfoPanel*****************//
        JPanel empInfos = new JPanel();
        empInfos.add(empLabels);
        empInfos.add(empTextFields);

        //**********************personalInfoPanel***********************//
        JPanel personInfos = new JPanel();
        personInfos.add(personInfoView.getLabelsPanel());
        personInfos.add(personInfoView.getTextFieldsPanel());

        //****************************buttons**************************//
        register = new JButton("Register");

        //***************************backgroundPanel*******************//
        backGround.add(empInfos, BorderLayout.EAST);
        backGround.add(personInfos, BorderLayout.WEST);
        backGround.add(register, BorderLayout.SOUTH);

        //*************************metaData***************************//
        setTitle("Register Doctor");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getContentPane().add(backGround, BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        requestFocus();
        setVisible(true);
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 1)
            registerBtnListener(listeners.get(0));
    }

    private void registerBtnListener(ActionListener actionListener) {
        register.addActionListener(actionListener);
    }

    private String getUserName() throws IllegalArgumentException {
        String str = userNameTextField.getText().trim();
        if (isValidLength(str))
            return str;
        else {
            displayMessage("username length must be at least 6 chars");
            throw new IllegalArgumentException();
        }
    }

    private String getPassword() throws IllegalArgumentException {
        String str = String.copyValueOf(passwordTextField.getPassword());
        if (isValidLength(str))
            return str;
        else {
            displayMessage("Password length must be at least 6 chars");
            throw new IllegalArgumentException();
        }
    }

    private String _getName() throws IllegalArgumentException {
        String str = personInfoView.getName();
        if (isValidLength(str))
            return str;
        else {
            displayMessage("name length must be at least 6 chars");
            throw new IllegalArgumentException();
        }
    }

    private String getPhoneNumber() throws IllegalArgumentException {
        String str = personInfoView.getPhoneNumber();
        if (isValidPhoneNumber(str)) {
            return str;
        } else {
            displayMessage("phone number length must be 12 only digits and start with 962");
            throw new IllegalArgumentException();
        }
    }

    private String getEmail() throws IllegalArgumentException {
        String email = personInfoView.getEmail();
        if (isValidEmailAddress(email) || email.isEmpty())
            return email;
        else {
            displayMessage("please enter a valid email address");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Doctor getInfo() throws IllegalArgumentException {
        try {
            return Doctor.getBuilder().
                    withUserName(getUserName()).
                    withPassword(getPassword()).
                    withName(_getName()).
                    withPhoneNumber(getPhoneNumber()).
                    withEmail(getEmail()).
                    build();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

}
