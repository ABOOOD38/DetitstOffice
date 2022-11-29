package views;

import views.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeView extends JFrame implements View {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private static final HomeView INSTANCE = new HomeView();
    private JButton addDoctor;
    private JButton addPatient;
    private JButton addAppointments;
    private JButton addSchedule;

    private HomeView() {
    }


    @Override
    public void display() {
        JPanel background = new JPanel();

        //**********************Buttons********************//
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));
        addDoctor = new JButton("Add Doctor");
        addPatient = new JButton("Add Patient");
        addSchedule = new JButton("Add Schedule");
        addAppointments = new JButton("Add Appointments");

        buttonsPanel.add(addDoctor);
        buttonsPanel.add(addPatient);
        buttonsPanel.add(addSchedule);
        buttonsPanel.add(addAppointments);
        background.add(buttonsPanel);


        //************************MetaData**********************//
        setTitle("Home");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        getContentPane().add(background, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
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

    public static HomeView getInstance() {
        return INSTANCE;
    }
}
