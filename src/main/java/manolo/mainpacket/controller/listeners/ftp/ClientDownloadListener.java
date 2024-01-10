package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.controllermodels.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
                        mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());

                        File selectedFile = fileChooser.getSelectedFile();

                        Files.write(selectedFile.toPath(), fileBytes);

                        Utils.showInfoWindow(mainController.getFtpWindowClient(), mainController.getMainViewModel().getFILE_DOWNLOAD_SUCCESS() + selectedFile.getAbsolutePath(), mainController.getMainViewModel().getINFO());

                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " download file: " + selectedFile.getName());
                        mainController.getMainConnection().closeConnection();
                    }
                } catch (Exception ex) {
                    Utils.showErrorWindow(mainController.getFtpWindowClient(), mainController.getMainViewModel().getFILE_DOWNLOAD_ERROR() + ex.getMessage(), mainController.getMainViewModel().getERROR());
                }
            } else {
                Utils.showWarningWindow(mainController.getFtpWindowClient(), mainController.getMainViewModel().getNO_FILE_SELECTED(), mainController.getMainViewModel().getWARNING());
            }
        }
    }
}
