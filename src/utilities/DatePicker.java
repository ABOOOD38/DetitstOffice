package utilities;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePicker {
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private final int year = Calendar.getInstance().get(Calendar.YEAR);
    private final JLabel label = new JLabel("", JLabel.CENTER);
    private String day = "";
    private final JDialog dialog;
    private final JButton[] buttons = new JButton[49];

    public DatePicker(JFrame parent) {
        dialog = new JDialog();
        dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        String[] header = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
        JPanel buttonsPanel = new JPanel(new GridLayout(7, 7));
        buttonsPanel.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < buttons.length; x++) {
            final int selection = x;
            buttons[x] = new JButton();
            buttons[x].setFocusPainted(false);
            buttons[x].setBackground(Color.white);
            if (x > 6)
                buttons[x].addActionListener(actionEvent -> {
                    day = buttons[selection].getActionCommand();
                    dialog.dispose();
                });
            if (x < 7) {
                buttons[x].setText(header[x]);
                buttons[x].setForeground(Color.red);
            }
            buttonsPanel.add(buttons[x]);
        }


        JPanel buttonsAndLabelsPanel = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<< Previous");
        previous.addActionListener(ae -> {
            month--;
            displayDate();
        });

        buttonsAndLabelsPanel.add(previous);
        buttonsAndLabelsPanel.add(label);
        JButton next = new JButton("Next >>");
        next.addActionListener(ae -> {
            month++;
            displayDate();
        });

        buttonsAndLabelsPanel.add(next);
        dialog.add(buttonsPanel, BorderLayout.CENTER);
        dialog.add(buttonsAndLabelsPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        displayDate();
        dialog.setVisible(true);
    }

    public void displayDate() {
        for (int x = 7; x < buttons.length; x++)
            buttons[x].setText("");

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            buttons[x].setText("" + day);

        label.setText(sdf.format(cal.getTime()));
        dialog.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals(""))
            return day;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

}
