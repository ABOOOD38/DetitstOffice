package views;

import models.Patient;
import views.interfaces.InfoView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static utilities.ValidationMethods.*;

public class RegisterPatientView extends JFrame implements InfoView<Patient> {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private final PersonInfoView personInfoView;
    private JButton register;

    public RegisterPatientView() {
        this.personInfoView = new PersonInfoView();
    }

    @Override
    public void display() {
        JPanel backGround = new JPanel();

        //********************buttons********************//
        register = new JButton("Register");

        //*****************backgroundPanel***************//
        backGround.add(personInfoView.getLabelsPanel());
        backGround.add(personInfoView.getTextFieldsPanel());

        //********************metaData******************//
        setTitle("Patient Register");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getContentPane().add(CENTER, backGround);
        getContentPane().add(SOUTH, register);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        requestFocus();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 1)
            registerBtnListener(listeners.get(0));
    }

    private void registerBtnListener(ActionListener actionListener) {
        this.register.addActionListener(actionListener);
    }

    private String _getName() throws IllegalArgumentException {
        String name = personInfoView.getName();
        if (isValidLength(name)) {
            return name;
        } else {
            displayMessage("name must be at least 6 chars long");
            throw new IllegalArgumentException();
        }
    }

    private String getPhoneNumber() throws IllegalArgumentException {
        String phoneNumber = personInfoView.getPhoneNumber();
        if (isValidPhoneNumber(phoneNumber)) {
            return phoneNumber;
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
    public Patient getInfo() throws IllegalArgumentException {
        try {
            return Patient.getBuilder().
                    withName(_getName()).
                    withPhoneNumber(getPhoneNumber()).
                    withEmail(getEmail()).
                    build();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

}
