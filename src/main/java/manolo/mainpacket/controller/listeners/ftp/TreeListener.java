package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreeListener implements TreeSelectionListener {

    private final MainController mainController;

    public TreeListener(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath selectedPath = mainController.getFtpWindow().getTreeDirectories().getSelectionPath();

        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            String nodeName = selectedNode.getUserObject().toString();

            mainController.getFtpWindow().getRenameField().setText(nodeName);

            StringBuilder selectedDirectory = new StringBuilder();
            for (Object pathComponent : selectedPath.getPath()) {
                selectedDirectory.append(pathComponent.toString());
            }

            mainController.getFtpWindow().setDirectory(selectedDirectory.toString());
            mainController.getFtpWindow().getRutaLabel().setText("Ruta actual: " + mainController.getFtpWindow().getDirectory());
        }
    }

    private void renameSelectedNode(String newFileName) {
        DefaultMutableTreeNode selectedNode = mainController.getFtpWindow().getSelectedNode();

        if (selectedNode != null) {
            int option = JOptionPane.showConfirmDialog(mainController.getFtpWindow(),
                    "¿Seguro que quieres cambiar el nombre a:\n" + newFileName,
                    "Confirmar cambio de nombre", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // Realizar el cambio de nombre
                selectedNode.setUserObject(newFileName);
                // Actualizar la vista del árbol
                mainController.getFtpWindow().getTreeDirectories().updateUI();
            }
        }
    }


}
