package manolo.mainpacket.controller.listeners.casos;

import manolo.mainpacket.controller.MainController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

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
            mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());

            String clientName = mainController.getCasosView().getComboClient().getSelectedItem().toString();
            String clientDni = mainController.getMainConnection().getDniFromName(clientName);
            String caso = mainController.getCasosView().getTextFieldCaso().getText();

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

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
