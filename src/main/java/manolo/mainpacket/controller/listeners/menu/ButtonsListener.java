package manolo.mainpacket.controller.listeners.menu;

import manolo.mainpacket.controller.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    private MainController mainController;

    public ButtonsListener(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (((JButton) e.getSource()).getName()) {
                    case "FTP" -> System.out.println("FTP");
                    case "EMAIL" -> mainController.openEmail();

                }
    }
}
