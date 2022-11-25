package views;

import models.Schedule;
import utilities.DatePicker;
import views.interfaces.TableInfoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ScheduleView extends JFrame implements TableInfoView<Schedule> {
    private final static int FRAME_WIDTH = 400;
    private final static int FRAME_HEIGHT = 200;
    private JButton showStart_S;
    private JButton showEnd_S;
    private JTextField startDateField_S;
    private JTextField endDateField_S;
    private JList<Integer> idList_S;
    private Integer idVal_S;

    private JButton add_S;
    private JButton remove_S;
    private JButton update_S;
    private JButton clear_S;
    private static JTable scheduleTable;

    public ScheduleView() {
    }


    @Override
    public void display() {
        JPanel background = new JPanel(new GridLayout(2, 1));


        JPanel table1 = new JPanel(new BorderLayout());// T1


        //***********************INPUT_LABELS1*******************************//
        JPanel labels1 = new JPanel(new GridLayout(2, 1));
        JLabel startAt = new JLabel("Start at: ", SwingConstants.CENTER);
        JLabel endAt = new JLabel("End at: ", SwingConstants.CENTER);
        labels1.add(startAt);
        labels1.add(endAt);
        //***********************INPUT_LABELS1******************************//


        //*********************INPUT_FIELDS1*********************************//
        JPanel fields1 = new JPanel(new GridLayout(2, 1));
        startDateField_S = new JTextField(20);
        endDateField_S = new JTextField(20);
        fields1.add(startDateField_S);
        fields1.add(endDateField_S);
        //*********************INPUT_FIELDS1*********************************//


        //**********************INPUT_BUTTONS1*******************************//
        JPanel buttons1 = new JPanel(new GridLayout(2, 1));
        showStart_S = new JButton("popup");
        showEnd_S = new JButton("popup");
        buttons1.add(showStart_S);
        buttons1.add(showEnd_S);
        //**********************INPUT_BUTTONS1*******************************//


        //**********************INPUT_ID_LIST1*******************************//
        idList_S = new JList<>();
        DefaultListModel<Integer> listModel = new DefaultListModel<>();

        idList_S.setSelectionMode(JList.VERTICAL);
        idList_S.setModel(listModel);
        idList_S.setVisibleRowCount(-1);
        listModel.addAll(List.of(1, 2, 3, 4, 5, 6, 8, 9));
        JScrollPane idListScroller = new JScrollPane(idList_S);

        //**********************INPUT_ID_LIST1*******************************//
        JPanel inputs1 = new JPanel(new GridLayout(1, 4));
        inputs1.add(labels1);
        inputs1.add(fields1);
        inputs1.add(buttons1);
        JPanel s = new JPanel(new GridLayout(1, 2));
        JLabel l = new JLabel("Select ID", SwingConstants.CENTER);
        s.add(l);
        s.add(idListScroller);
        table1.add(s,BorderLayout.CENTER);

        table1.add(inputs1, BorderLayout.NORTH);
        //*****************************************INPUTS_1***********************************//


        //****************SCHEDULE_TABLE***************//
        scheduleTable.setDefaultEditor(Object.class, null);
        table1.add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
        //****************SCHEDULE_TABLE***************//


        //***************BUTTONS1***********************//
        JPanel buttons_S = new JPanel(new GridLayout(1, 4));
        add_S = new JButton("Add");
        remove_S = new JButton("Remove");
        update_S = new JButton("Update");
        clear_S = new JButton("Clear");
        buttons_S.add(add_S);
        buttons_S.add(remove_S);
        buttons_S.add(update_S);
        buttons_S.add(clear_S);
        table1.add(buttons_S, BorderLayout.SOUTH);
        //***************BUTTONS1***********************//


        getContentPane().add(table1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Doctor Schedules");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        requestFocus();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public Schedule getInfo() {
        return null;
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        showStartListener();
        showEndListener();
        idListListener();
        clear_SListener();

        add_SListener(listeners.get(0));
        remove_SListener(listeners.get(1));
        update_SListener(listeners.get(2));
    }

    private void showStartListener() {
        showStart_S.addActionListener(ae -> startDateField_S.setText(new DatePicker(this).setPickedDate()));
    }

    private void showEndListener() {
        showEnd_S.addActionListener(ae -> endDateField_S.setText(new DatePicker(this).setPickedDate()));
    }

    private void idListListener() {
        idList_S.getSelectionModel().addListSelectionListener(ac -> idVal_S = idList_S.getSelectedValue());
    }

    private void add_SListener(ActionListener actionListener) {
        add_S.addActionListener(actionListener);
    }

    private void remove_SListener(ActionListener actionListener) {
        remove_S.addActionListener(actionListener);
    }

    private void update_SListener(ActionListener actionListener) {
        update_S.addActionListener(actionListener);
    }

    private void clear_SListener() {
        clear_S.addActionListener(actionEvent -> {
            startDateField_S.setText("");
            endDateField_S.setText("");
        });
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
    public void setInfo(Schedule info) {
        scheduleTable = new JTable(info.rows(), info.cols());

    }
}
