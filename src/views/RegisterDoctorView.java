package views;

import models.Doctor;
import models.Employee;
import models.PersonInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static models.utilities.ValidationMethods.isValidEmailAddress;
import static models.utilities.ValidationMethods.isValidPhoneNumber;

public class RegisterDoctorView extends JFrame implements InfoView<Doctor> {
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

        JLabel userName = new JLabel("userName");
        JLabel password = new JLabel("password");

        JPanel empLabels = new JPanel(new GridLayout(3, 1));
        empLabels.add(userName);
        empLabels.add(password);

        userNameTextField = new JTextField(15);
        passwordTextField = new JPasswordField(15);

        JPanel empTextFields = new JPanel(new GridLayout(3, 1));
        empTextFields.add(userNameTextField);
        empTextFields.add(passwordTextField);

        JPanel empInfos = new JPanel();
        empInfos.add(empLabels);
        empInfos.add(empTextFields);

//****************************************************************************//

        JPanel personInfos = new JPanel();
        personInfos.add(personInfoView.getLabelsPanel());
        personInfos.add(personInfoView.getTextFieldsPanel());

//******************************************************************************//
        register = new JButton("Register");
//*****************************************************************************//

        JPanel backGround = new JPanel(new BorderLayout());

        backGround.add(empInfos, BorderLayout.EAST);
        backGround.add(personInfos, BorderLayout.WEST);
        backGround.add(register, BorderLayout.SOUTH);

        this.getContentPane().add(backGround, BorderLayout.CENTER);
        this.setTitle("Register Doctor");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 1)
            registerBtnListener(listeners.get(0));
    }


    @Override
    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }


    @Override
    public JFrame getJFrame() {
        return this
                ;
    }

    private void registerBtnListener(ActionListener actionListener) {
        register.addActionListener(actionListener);
    }

    private String getUserName() throws IllegalArgumentException {
        String str = userNameTextField.getText().trim();
        if (_isValidLength(str))
            return str;
        else {
            displayMessage("username length must be at least 6 chars");
            throw new IllegalArgumentException();
        }
    }

    private String getPassword() throws IllegalArgumentException {
        String str = String.copyValueOf(passwordTextField.getPassword());
        if (_isValidLength(str))
            return str;
        else {
            displayMessage("Password length must be at least 6 chars");
            throw new IllegalArgumentException();
        }
    }

    private String _getName() throws IllegalArgumentException {
        String str = personInfoView.getName();
        if (_isValidLength(str))
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

    private boolean _isValidLength(String str) {
        return str.length() >= 6;
    }

    @Override
    public Doctor getInfo() throws IllegalArgumentException {
        PersonInfo personInfo;

        if (getEmail().isEmpty())
            personInfo = new PersonInfo(_getName(), getPhoneNumber());
        else
            personInfo = new PersonInfo(_getName(), getPhoneNumber(), getEmail());

        try {
            return new Doctor(new Employee(getUserName(), getPassword()), personInfo);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException();
        }
    }
}
