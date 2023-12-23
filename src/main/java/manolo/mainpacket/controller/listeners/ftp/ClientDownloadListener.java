package manolo.mainpacket.controller.listeners.ftp;

import com.mysql.cj.xdevapi.Client;
import manolo.mainpacket.controller.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ClientDownloadListener implements ActionListener {

    private final MainController mainController;

    public ClientDownloadListener(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainController.getFtpWindowClient().getDownloadButton()) {
            JList listFiles = mainController.getFtpWindowClient().getListFiles();
            String selectedFileName = (String) listFiles.getSelectedValue();

            if (selectedFileName != null) {
                String filePath = "/" + mainController.getCurrentUser().getDni() + "/" + mainController.getMainClient() + "/" + selectedFileName;

                try {
                    byte[] fileBytes = mainController.getFtpService().downloadFile(filePath, mainController.getMainClient());

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Guardar archivo");
                    fileChooser.setSelectedFile(new File(selectedFileName));
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos", ".*"));

                    int userSelection = fileChooser.showSaveDialog(mainController.getFtpWindowClient());

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();

                        Files.write(selectedFile.toPath(), fileBytes);

                        mainController.showInfoWindow(mainController.getFtpWindowClient(), "Archivo descargado exitosamente en la ruta seleccionada.");
                    }
                } catch (Exception ex) {
                    mainController.showErrorWindow(mainController.getFtpWindowClient(), "Error al descargar el archivo: " + ex.getMessage());
                }
            } else {
                mainController.showWarningWindow(mainController.getFtpWindowClient(), "Ningún archivo seleccionado para descargar.");
            }
        }
    }
}
