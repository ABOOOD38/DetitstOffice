package views;

import javax.swing.*;
import java.awt.*;

public class PatientAppointmentView extends JFrame {

    private JTable table;
    private String[][] tableData;
    private String[] tableCols;


    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;


    public PatientAppointmentView() {
    }

    private void initComponents() {// TODO: 11/29/22

        table = new JTable(tableData, tableCols);

        table.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(table);


        getContentPane().add(sp, BorderLayout.CENTER);
        setTitle("Patients");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


    }

    public void display() {
        initComponents();
    }


    public void setTableData(String[][] data) {
        this.tableData = data;
    }

    public void setTableCols(String[] cols) {
        this.tableCols = cols;
    }
}
