package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeysListener implements KeyListener {
    private MainController mainController;

    public KeysListener(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            showConfirmationDialog();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void showConfirmationDialog() {
        int option = JOptionPane.showConfirmDialog(
                mainController.getFtpWindow(),
                "¿Seguro deseas renombrar?",
                "Renombrar",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
        }
    }
}
