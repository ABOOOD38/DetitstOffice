package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class IndexView extends JFrame implements View {
    private JButton logIn;
    private final static int FRAME_WIDTH = 350;
    private final static int FRAME_HEIGHT = 350;

    public IndexView() {
    }

    @Override
    public void display() {
        setTitle("Dentist Office");

        logIn = new JButton("LogIn");
        JPanel backGround = new JPanel();

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 1));
        buttonsPanel.add(logIn);

        backGround.add(buttonsPanel);
        getContentPane().add(backGround, BorderLayout.CENTER);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        requestFocus();
        setLocationRelativeTo(null);
        logIn.requestFocus();
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 1)
            logInBtnListener(listeners.get(0));
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

    @Override
    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void logInBtnListener(ActionListener actionListener) {
        logIn.addActionListener(actionListener);
    }
}

