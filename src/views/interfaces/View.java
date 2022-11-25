package views.interfaces;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public interface View {
    void display();

    void registerListeners(List<ActionListener> listeners);

    void displayMessage(String msg);

    JFrame getJFrame();

}
