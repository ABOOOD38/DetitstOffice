package views;

import views.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeView extends JFrame implements View {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    private JButton addDoctor;
    private JButton addPatient;
    private JButton addAppointments;
    private JButton addSchedule;

    private static final HomeView INSTANCE = new HomeView();

    private HomeView() {
    }

    public static HomeView getInstance() {
        return INSTANCE;
    }

    @Override
    public void display() {
        JPanel background = new JPanel();

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
        addDoctor = new JButton("Add Doctor");
        addPatient = new JButton("Add Patient");
        addAppointments = new JButton("Add Appointments");
        addSchedule = new JButton("Add Schedule");

        buttonsPanel.add(addDoctor);
        buttonsPanel.add(addPatient);
        buttonsPanel.add(addAppointments);
        buttonsPanel.add(addSchedule);
        background.add(buttonsPanel);

        getContentPane().add(background, BorderLayout.CENTER);
        setTitle("Home");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();
        setLocationRelativeTo(null);

        setVisible(true);
    }


    @Override
    public void registerListeners(List<ActionListener> listeners) {
        if (listeners.size() == 4) {
            addDoctorBtnListener(listeners.get(0));
            addPatientBtnListener(listeners.get(1));
            addAppointmentsBtnListener(listeners.get(2));
            addScheduleBtnListener(listeners.get(3));
        }
    }

    private void addScheduleBtnListener(ActionListener actionListener) {
        addSchedule.addActionListener(actionListener);

    }

    private void addDoctorBtnListener(ActionListener actionListener) {
        addDoctor.addActionListener(actionListener);
    }

    private void addPatientBtnListener(ActionListener actionListener) {
        addPatient.addActionListener(actionListener);
    }

    private void addAppointmentsBtnListener(ActionListener actionListener) {
        addAppointments.addActionListener(actionListener);
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
