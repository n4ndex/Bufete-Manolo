package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
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
            int option = JOptionPane.showConfirmDialog(
                    mainController.getFtpWindow(),
                    "¿Seguro deseas renombrar?",
                    "Renombrar",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                renameSelectedFile();
            }
        }
    }

    private void renameSelectedFile() {
        DefaultMutableTreeNode selectedNode = mainController.getFtpWindow().getSelectedNode();
        if (selectedNode != null) {
            String oldName = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);
            String newName = mainController.getFtpWindow().getRenameField().getText();
            String currentPath = mainController.getFtpWindow().getDirectory() + "/" + oldName;
            String newPath = mainController.getFtpWindow().getDirectory() + "/" + newName;

            try {
                mainController.getFtpService().renameFile(currentPath, newPath, mainController.getMainClient());
                mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                mainController.showInfoWindow(mainController.getFtpWindow(), "Archivo renombrado exitosamente.");
            } catch (Exception e) {
                mainController.showErrorWindow(mainController.getFtpWindow(), "Error al renombrar el archivo: " + e.getMessage());
            }
        } else {
            mainController.showWarningWindow(mainController.getFtpWindow(), "Ningún archivo seleccionado para renombrar.");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
