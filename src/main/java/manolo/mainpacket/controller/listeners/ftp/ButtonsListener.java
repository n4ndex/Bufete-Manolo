package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ButtonsListener implements ActionListener {

    private final MainController mainController;

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
        if (e.getSource() == mainController.getFtpWindow().getExitButton()) {   // exit to menu button
            mainController.getFtpWindow().dispose();
            mainController.getMenu().setVisible(true);
        } else if (e.getSource() == mainController.getFtpWindow().getCreateDirButton()) {   // create dir button
            TreePath selectedPath = mainController.getFtpWindow().getTreeDirectories().getSelectionPath();

            if (selectedPath != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                String selectedDirectory = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);
                System.out.println("DIREC SELEC. "+selectedDirectory);

                String folderName = JOptionPane.showInputDialog(mainController.getFtpWindow(), "Ingrese el nombre de la carpeta:");

                if (folderName != null && !folderName.isEmpty()) {
                    // Asegurarse de que la nueva carpeta se cree dentro de la carpeta seleccionada
                    String newDirectoryPath = selectedDirectory + File.separator + folderName;

                    mainController.getFtpService().createDirectory(newDirectoryPath, mainController.getMainClient(), mainController.getFtpWindow());
                    mainController.getFtpWindow().loadDirectory(mainController.getMainClient());
                } else {
                    JOptionPane.showMessageDialog(mainController.getFtpWindow(), "Error al crear la carpeta.");
                }
            } else {
                JOptionPane.showMessageDialog(mainController.getFtpWindow(), "Seleccione una carpeta origen antes de crear una nueva.");
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDeleteDirButton()) {   // delete dir button
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                String selectedDirectoryPath = "" + selectedNode.getUserObject();

                int option = JOptionPane.showConfirmDialog(mainController.getFtpWindow(),
                        "¿Seguro que quieres eliminar el directorio?\n" + selectedDirectoryPath,
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    if (mainController.getFtpService().deleteDirectory(selectedDirectoryPath, mainController.getMainClient(), mainController.getFtpWindow())) {
                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient());
                    } else {
                        JOptionPane.showMessageDialog(mainController.getFtpWindow(), "Error al eliminar el directorio.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(mainController.getFtpWindow(), "Selecciona un directorio para eliminar.");
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDeleteFileButton()) {

        } else if (e.getSource() == mainController.getFtpWindow().getDownloadButton()) {

        } else if (e.getSource() == mainController.getFtpWindow().getUploadButton()) {

        } else if (e.getSource() == mainController.getFtpWindow().getRefreshButton()) {
            mainController.getFtpWindow().loadDirectory(mainController.getMainClient());
        }
    }
}
