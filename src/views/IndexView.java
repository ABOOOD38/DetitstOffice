package views;

import views.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class IndexView extends JFrame implements View {
    private JButton logIn;
    private final static int FRAME_WIDTH = 250;
    private final static int FRAME_HEIGHT = 250;

    public IndexView() {
    }

    @Override
    public void display() {
        JPanel backGround = new JPanel();

        //***************Buttons*******************//
        JPanel buttonsPanel = new JPanel();
        logIn = new JButton("LogIn");
        buttonsPanel.add(logIn);
        backGround.add(buttonsPanel);


        //*****************MetaData*****************//
        setTitle("Dentist Office");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getContentPane().add(backGround, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        logIn.requestFocus();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 1)
            logInBtnListener(listeners.get(0));
    }

    private void logInBtnListener(ActionListener actionListener) {
        logIn.addActionListener(actionListener);
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

