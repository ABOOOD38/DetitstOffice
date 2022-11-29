package views;

import models.LoginInfo;
import views.interfaces.InfoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SignInView extends JFrame implements InfoView<LoginInfo> {
    private JTextField userNameTextFiled;
    private JPasswordField passwordTextFiled;
    private JButton signIn;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    public SignInView() {
    }

    @Override
    public void display() {
        JPanel background = new JPanel(new BorderLayout());

        //********************labels********************//
        JLabel userNameLabel = new JLabel("UserName");
        JLabel passwordLabel = new JLabel("Password");
        JPanel labelsPanel = new JPanel(new GridLayout(2, 1));
        labelsPanel.add(userNameLabel);
        labelsPanel.add(passwordLabel);
        background.add(labelsPanel, BorderLayout.WEST);


        //******************TextFields*****************//
        userNameTextFiled = new JTextField(15);
        passwordTextFiled = new JPasswordField(15);
        JPanel textFieldsPanel = new JPanel(new GridLayout(2, 1));
        textFieldsPanel.add(userNameTextFiled);
        textFieldsPanel.add(passwordTextFiled);
        background.add(textFieldsPanel, BorderLayout.EAST);


        //*******************Buttons*******************//
        signIn = new JButton("Sign In");
        background.add(signIn, BorderLayout.SOUTH);


        //*****************MetaData**********************//
        setTitle("Sign in");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getContentPane().add(background, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        userNameTextFiled.requestFocus();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 1)
            SignInBtnListener(listeners.get(0));
    }

    private void SignInBtnListener(ActionListener actionListener) {
        signIn.addActionListener(actionListener);
    }

    private String getUserName() throws IllegalArgumentException {
        String userName = userNameTextFiled.getText().trim();

        if (userName.isEmpty())
            throw new IllegalArgumentException();
        return userName;
    }

    private String getPassword() throws IllegalArgumentException {
        String password = String.copyValueOf(passwordTextFiled.getPassword());

        if (password.isEmpty())
            throw new IllegalArgumentException();
        return password;
    }

    @Override
    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

    @Override
    public LoginInfo getInfo() {
        return LoginInfo.getBuilder().
                withUserName(getUserName()).
                withPassword(getPassword()).
                build();
    }
}
