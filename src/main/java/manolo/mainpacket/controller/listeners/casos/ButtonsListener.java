/**
 * ButtonsListener - ActionListener for buttons related to case management in the MainPacket application.
 * This class handles button clicks associated with case management, including updating client assignments,
 * creating directories and empty files on the FTP server, and inserting logs into the database.
 */

package manolo.mainpacket.controller.listeners.casos;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.controllermodels.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * ButtonsListener - ActionListener for buttons related to case management in the MainPacket application.
 * This class handles button clicks associated with case management, including updating client assignments, creating directories and empty files on the FTP server, and inserting logs into the database.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
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
        mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());
        String caso = mainController.getCasosView().getTextFieldCaso().getText().trim();
        if (caso.isEmpty()) {
            Utils.showWarningWindow(mainController.getCasosView(), mainController.getMainViewModel().getCASE_EMPTY(), mainController.getMainViewModel().getWARNING());
            mainController.getMainConnection().closeConnection();
            return;
        }
        String clientName = mainController.getCasosView().getComboClient().getSelectedItem().toString();
        String clientDni = mainController.getMainConnection().getDniFromName(clientName);
        int newLawyerId = mainController.getMainConnection().getIdFromName(mainController.getCurrentUser().getName());
        mainController.getMainConnection().updateClientLawyerId(clientName, newLawyerId);
        String currentUserFolderPath = File.separator + mainController.getCurrentUser().getDni();
        String casoFolderPath = currentUserFolderPath + File.separator + caso;
        mainController.getFtpService().createDirectory(casoFolderPath, mainController.getMainClient(), mainController.getFtpWindow());
        String clientFolderPath = casoFolderPath + File.separator + clientDni;
        mainController.getFtpService().createDirectory(clientFolderPath, mainController.getMainClient(), mainController.getFtpWindow());
        String emptyFileName = "empty_file.txt";
        String emptyFilePath = clientFolderPath + File.separator + emptyFileName;
        mainController.getFtpService().createEmptyFile(emptyFilePath, mainController.getMainClient());
        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), "lawyer " + mainController.getCurrentUser().getName() + " assigned to " + clientName + " case: " + caso);
        mainController.getMainConnection().closeConnection();
    }
}
