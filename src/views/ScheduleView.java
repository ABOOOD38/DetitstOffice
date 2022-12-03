package views;

import models.Schedule;
import models.TableView;
import utilities.DatePicker;
import views.interfaces.TableInfoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static utilities.ValidationMethods.isValidTime;

public class ScheduleView extends JFrame implements TableInfoView<TableView, Schedule> {
    private final static int FRAME_WIDTH = 400;
    private final static int FRAME_HEIGHT = 200;
    private JButton showStart, showEnd;
    private JTextField startDateField, endDateField;
    private int idVal;
    private JButton remove, update, clear;
    private static JTable scheduleTable;

    private Map<String, JCheckBox> jCheckBoxes;

    private JSpinner startTime, endTime;

    public ScheduleView() {
    }


    @Override
    public void display() {
        JPanel schedulePanel = new JPanel(new BorderLayout());

        //***********************LABELS*******************************//
        JPanel labels = new JPanel(new GridLayout(2, 1));
        JLabel startAt = new JLabel("Start at: ", SwingConstants.CENTER);
        JLabel endAt = new JLabel("End at: ", SwingConstants.CENTER);
        labels.add(startAt);
        labels.add(endAt);

        //********************CHECKBOXES**************************//
        JPanel checkBoxes = new JPanel(new GridLayout(1, 7));
        jCheckBoxes = new LinkedHashMap<>();
        jCheckBoxes.put("sun", new JCheckBox("Sunday"));
        jCheckBoxes.put("mon", new JCheckBox("Monday"));
        jCheckBoxes.put("tue", new JCheckBox("Tuesday"));
        jCheckBoxes.put("wed", new JCheckBox("Wednesday"));
        jCheckBoxes.put("thu", new JCheckBox("Thursday"));
        jCheckBoxes.put("fri", new JCheckBox("Friday"));
        jCheckBoxes.put("sat", new JCheckBox("Saturday"));

        checkBoxes.add(jCheckBoxes.get("sun"));
        checkBoxes.add(jCheckBoxes.get("mon"));
        checkBoxes.add(jCheckBoxes.get("tue"));
        checkBoxes.add(jCheckBoxes.get("wed"));
        checkBoxes.add(jCheckBoxes.get("thu"));
        checkBoxes.add(jCheckBoxes.get("fri"));
        checkBoxes.add(jCheckBoxes.get("sat"));


        //***********************JSpinners***************************//
        String[] times = {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00",
                "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00",
                "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00",
                "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00",
                "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30",
        };

        SpinnerModel valuesS = new SpinnerListModel(times);
        SpinnerModel valuesE = new SpinnerListModel(times);
        startTime = new JSpinner(valuesS);
        endTime = new JSpinner(valuesE);
        startTime.setEditor(new JSpinner.DefaultEditor(startTime));
        endTime.setEditor(new JSpinner.DefaultEditor(endTime));


        //*********************FIELDS*********************************//
        JPanel fields = new JPanel(new GridLayout(2, 1));
        startDateField = new JTextField(20);
        endDateField = new JTextField(20);
        startDateField.setBackground(Color.WHITE);
        endDateField.setBackground(Color.WHITE);
        startDateField.setEditable(false);
        endDateField.setEditable(false);
        fields.add(startDateField);
        fields.add(endDateField);


        //**********************BUTTONS*******************************//
        JPanel buttons = new JPanel(new GridLayout(2, 1));
        showStart = new JButton("select date");
        showEnd = new JButton("select date");
        buttons.add(showStart);
        buttons.add(showEnd);


        //*****************************************INPUTS***********************************//
        JPanel inputs1 = new JPanel(new GridLayout(1, 3));
        inputs1.add(labels);
        inputs1.add(fields);
        inputs1.add(buttons);

        JPanel inputs2 = new JPanel(new GridLayout(2, 1));
        JPanel inputs2_1 = new JPanel(new GridLayout(1, 4));
        inputs2_1.add(new JLabel("Start Time: ", SwingConstants.CENTER));
        inputs2_1.add(startTime);
        inputs2_1.add(new JLabel("End Time: ", SwingConstants.CENTER));
        inputs2_1.add(endTime);

        inputs2.add(checkBoxes);
        inputs2.add(inputs2_1);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(inputs1);
        splitPane.setBottomComponent(inputs2);
        schedulePanel.add(splitPane, BorderLayout.NORTH);

        //****************SCHEDULE_TABLE***************//
        scheduleTable.setDefaultEditor(Object.class, null);
        scheduleTable.setCellSelectionEnabled(true);
        JScrollPane scheduleTableScroll = new JScrollPane(scheduleTable);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setTopComponent(splitPane);
        splitPane2.setBottomComponent(scheduleTableScroll);

        schedulePanel.add(splitPane2, BorderLayout.CENTER);

        //***************BUTTONS***********************//
        JPanel _buttons = new JPanel(new GridLayout(1, 3));
        update = new JButton("Update");
        remove = new JButton("Remove");
        clear = new JButton("Clear");
        _buttons.add(update);
        _buttons.add(remove);
        _buttons.add(clear);
        schedulePanel.add(_buttons, BorderLayout.SOUTH);


        //******************MetaData***********************//
        setTitle("Doctor Schedules");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(schedulePanel);
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
    }

    @Override
    public void registerListeners(List<ActionListener> listeners) {
        showStartListener();
        showEndListener();
        clearListener();
        scheduleSelectionListener();

        if (listeners.size() == 2) {
            removeListener(listeners.get(0));
            updateListener(listeners.get(1));
        }
    }

    private void showStartListener() {
        showStart.addActionListener(ae -> startDateField.setText(new DatePicker(this).setPickedDate()));
    }

    private void showEndListener() {
        showEnd.addActionListener(ae -> endDateField.setText(new DatePicker(this).setPickedDate()));
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
            if (row != -1) {
                idVal = Integer.parseInt(scheduleTable.getModel().getValueAt(row, column).toString());
                System.err.println(idVal);
            }
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
    public void setTableInfo(TableView info) {
        if (scheduleTable != null) {
            DefaultTableModel tableModel = (DefaultTableModel) scheduleTable.getModel();
            tableModel.setColumnCount(0);
            tableModel.setDataVector(info.rows(), info.cols());
            scheduleTable.setModel(tableModel);
            tableModel.fireTableDataChanged();
        } else
            scheduleTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setDataVector(info.rows(), info.cols());
        scheduleTable.setModel(tableModel);
    }

    @Override
    public Integer getSelectedID() {
        return idVal;
    }

    @Override
    public void resetSelectedID() {
        this.idVal = 0;
    }

    @Override
    public Schedule getInfo() {
        try {
            final LocalDate start = LocalDate.parse(startDateField.getText());
            final LocalDate end = LocalDate.parse(endDateField.getText());

            String days = getDays();
            if (days.isEmpty()) {
                displayMessage("please choose at least one day");
                return null;
            }

            Time startTimeTime = getTime(startTime.getValue());
            Time endTimeTime = getTime(endTime.getValue());
            int timeValidRes = isValidTime(startTimeTime, endTimeTime);

            if (timeValidRes != 3) {
                showTimeError(timeValidRes);
                return null;
            }
            System.err.println(timeValidRes);

            return Schedule.getBuilder().
                    withStartAt(start).
                    withEndAt(end).
                    withDays(days).
                    withStartTime(startTimeTime).
                    withEndTime(endTimeTime).
                    build();
        } catch (DateTimeParseException e) {
            displayMessage("please choose start and end date");
            return null;
        }
    }

    private Time getTime(Object toConvert) {
        String time = (String) toConvert;
        time += ":00";
        return Time.valueOf(time);
    }

    private void showTimeError(int timeRes) {
        switch (timeRes) {
            case 1 -> displayMessage("start time and end time shouldn't be the same");
            case 2 -> displayMessage("start time should be before end time");
        }
    }

    private String getDays() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, JCheckBox> entry : jCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                stringBuilder.append(entry.getKey()).append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
