package views;

import javax.swing.*;
import java.awt.*;

public class PersonInfoView {
    private JTextField nameTextField;
    private JTextField phoneNumberTextField;
    private JTextField emailTextField;
    private JPanel labels;
    private JPanel textFields;

    public PersonInfoView() {
        initComponent();
    }

    public void initComponent() {
        JLabel nameLabel = new JLabel("name");
        JLabel phoneNumber = new JLabel("PhoneNumber");
        JLabel email = new JLabel("email");

        labels = new JPanel(new GridLayout(3, 1));

        labels.add(nameLabel);
        labels.add(phoneNumber);
        labels.add(email);

        nameTextField = new JTextField(15);
        phoneNumberTextField = new JTextField(15);
        emailTextField = new JTextField(15);

        textFields = new JPanel(new GridLayout(3, 1));

        textFields.add(nameTextField);
        textFields.add(phoneNumberTextField);
        textFields.add(emailTextField);

    }

    public String getName() {
        return nameTextField.getText().trim();
    }

    public String getPhoneNumber() {
        return phoneNumberTextField.getText().trim();
    }

    public String getEmail() {
        try {
            return emailTextField.getText().trim();
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public JPanel getLabelsPanel() {
        return this.labels;
    }

    public JPanel getTextFieldsPanel() {
        return this.textFields;
    }
}
