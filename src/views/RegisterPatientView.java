package views;

import models.Patient;
import models.PersonInfo;
import views.interfaces.InfoView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static utilities.ValidationMethods.isValidEmailAddress;
import static utilities.ValidationMethods.isValidPhoneNumber;

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
        register = new JButton("Register");

        backGround.add(personInfoView.getLabelsPanel());
        backGround.add(personInfoView.getTextFieldsPanel());

        getContentPane().add(CENTER, backGround);
        getContentPane().add(SOUTH, register);
        setTitle("Patient Register");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        pack();
        requestFocus();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        registerBtnListener(listeners.get(0));
    }

    private void registerBtnListener(ActionListener actionListener) {
        this.register.addActionListener(actionListener);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public Patient getInfo() throws IllegalArgumentException {
        PersonInfo personInfo;
        try {
            if (getEmail().isEmpty())
                personInfo = new PersonInfo(_getName(), getPhoneNumber());
            else
                personInfo = new PersonInfo(_getName(), getPhoneNumber(), getEmail());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
        return new Patient(personInfo, new BigDecimal(0), new BigDecimal(0), null);
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

    private String _getName() throws IllegalArgumentException {
        String name = personInfoView.getName();
        if (name.length() >= 6) {
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

}
