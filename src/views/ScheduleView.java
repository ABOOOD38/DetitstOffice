package views;

import models.Schedule;
import models.ScheduleTable;
import org.jetbrains.annotations.Nullable;
import utilities.DatePicker;
import views.interfaces.TableInfoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ScheduleView extends JFrame implements TableInfoView<ScheduleTable> {
    private final static int FRAME_WIDTH = 400;
    private final static int FRAME_HEIGHT = 200;
    private JButton showStar;
    private JButton showEnd;
    private JTextField startDateField;
    private JTextField endDateField;
    private JList<Integer> idList;
    private int idVal;

    private JButton add;
    private JButton remove;
    private JButton update;
    private JButton clear;
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
        startDateField = new JTextField(20);
        endDateField = new JTextField(20);
        startDateField.setBackground(Color.WHITE);
        endDateField.setBackground(Color.WHITE);
        startDateField.setEditable(false);
        endDateField.setEditable(false);
        fields1.add(startDateField);
        fields1.add(endDateField);
        //*********************INPUT_FIELDS1*********************************//


        //**********************INPUT_BUTTONS1*******************************//
        JPanel buttons1 = new JPanel(new GridLayout(2, 1));
        showStar = new JButton("popup");
        showEnd = new JButton("popup");
        buttons1.add(showStar);
        buttons1.add(showEnd);
        //**********************INPUT_BUTTONS1*******************************//


        //**********************INPUT_ID_LIST1*******************************//
        idList = new JList<>();
        DefaultListModel<Integer> listModel = new DefaultListModel<>();

        idList.setSelectionMode(JList.VERTICAL);
        idList.setModel(listModel);
        idList.setVisibleRowCount(-1);
        listModel.addAll(List.of(1, 2, 3, 4, 5, 6, 8, 9));
        JScrollPane idListScroller = new JScrollPane(idList);

        //**********************INPUT_ID_LIST1*******************************//
        JPanel inputs1 = new JPanel(new GridLayout(1, 4));
        inputs1.add(labels1);
        inputs1.add(fields1);
        inputs1.add(buttons1);
        JPanel s = new JPanel(new GridLayout(1, 2));
        JLabel l = new JLabel("Select ID", SwingConstants.CENTER);
        s.add(l);
        s.add(idListScroller);
        table1.add(s, BorderLayout.CENTER);

        table1.add(inputs1, BorderLayout.NORTH);
        //*****************************************INPUTS_1***********************************//


        //****************SCHEDULE_TABLE***************//
        scheduleTable.setDefaultEditor(Object.class, null);
        scheduleTable.setCellSelectionEnabled(true);

        table1.add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
        //****************SCHEDULE_TABLE***************//


        //***************BUTTONS1***********************//
        JPanel buttons_S = new JPanel(new GridLayout(1, 4));
        add = new JButton("Add");
        remove = new JButton("Remove");
        update = new JButton("Update");
        clear = new JButton("Clear");
        buttons_S.add(add);
        buttons_S.add(remove);
        buttons_S.add(update);
        buttons_S.add(clear);
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
    public ScheduleTable getInfo() {
        return null;
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        showStartListener();
        showEndListener();
        idListListener();
        clearListener();
        scheduleSelectionListener();

        addListener(listeners.get(0));
        removeListener(listeners.get(1));
        updateListener(listeners.get(2));
    }

    private void showStartListener() {
        showStar.addActionListener(ae -> startDateField.setText(new DatePicker(this).setPickedDate()));
    }

    private void showEndListener() {
        showEnd.addActionListener(ae -> endDateField.setText(new DatePicker(this).setPickedDate()));
    }

    private void idListListener() {
        idList.getSelectionModel().addListSelectionListener(ac -> idVal = idList.getSelectedValue());
    }

    private void addListener(ActionListener actionListener) {
        add.addActionListener(actionListener);
    }

    private void removeListener(ActionListener actionListener) {
        remove.addActionListener(actionListener);
    }

    private void updateListener(ActionListener actionListener) {
        update.addActionListener(actionListener);
    }

    private void clearListener() {
        clear.addActionListener(actionEvent -> {
            startDateField.setText("");
            endDateField.setText("");
        });
    }

    private void scheduleSelectionListener() {
        ListSelectionModel selection = getTableSelectionModel();
        selection.addListSelectionListener(listSelectionEvent -> {
            int column = 0;
            int row = scheduleTable.getSelectedRow();
            idVal = Integer.parseInt(scheduleTable.getModel().getValueAt(row, column).toString());
            System.err.println(idVal);
        });
    }

    @Override
    public void displayMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private ListSelectionModel getTableSelectionModel() {
        ListSelectionModel selectionModel = scheduleTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return selectionModel;
    }

    @Override
    public JFrame getJFrame() {
        return this;
    }

    @Override
    public void setInfo(ScheduleTable info) {
        scheduleTable = new JTable(info.rows(), info.cols());

    }

    @Override
    public @Nullable Schedule getSelectedSchedule() {
        try {
            final LocalDate start = LocalDate.parse(startDateField.getText());
            final LocalDate end = LocalDate.parse(endDateField.getText());
            return new Schedule(idVal, start, end);
        } catch (DateTimeParseException e) {
            displayMessage("please choose start and end date");
            return null;
        }
    }
}
