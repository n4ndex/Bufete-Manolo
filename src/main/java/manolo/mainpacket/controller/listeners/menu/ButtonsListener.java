package manolo.mainpacket.controller.listeners.menu;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.smptGmail.ReceiveEmail;
import manolo.mainpacket.view.*;
import manolo.mainpacket.model.viewmodels.EmailTexts_es;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    private final MainController mainController;
    private final boolean isLawyer;

    public ButtonsListener(MainController mainController, boolean isLawyer) {
        this.mainController = mainController;
        this.isLawyer = isLawyer;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());

        mainController.getMenu().dispose();
        switch (((JButton) e.getSource()).getName()) {
            case "FTP" -> {
                if (isLawyer) {
                    mainController.setFtpWindow(new FTPWindow(mainController));
                    mainController.getFtpWindow().setLawyerName(mainController.getCurrentUser().getName());
                    mainController.getFtpWindow().setLawyerDni(mainController.getCurrentUser().getDni());
                    mainController.getFtpWindow().loadDirectory(mainController.getMainClient(),mainController.getFtpWindow().getLawyerDni());
                    mainController.getFtpWindow().getNameLabel().setText(mainController.getFtpWindow().getModel().getTextsList().get(0) + mainController.getFtpWindow().getLawyerName());
                    mainController.getFtpWindow().getServerLabel().setText(mainController.getFtpWindow().getModel().getTextsList().get(2) + mainController.getMainClient().getLocalAddress());
                    mainController.addFtpLawyerEventListeners();
                } else {
                    mainController.setFtpWindowClient(new FTPWindowClient(mainController));
                    mainController.getFtpWindowClient().setClientName(mainController.getCurrentUser().getName());
                    mainController.getFtpWindowClient().getNameLabel().setText(mainController.getFtpWindowClient().getModel().getTextsList().get(0) + mainController.getFtpWindowClient().getClientName());
                    mainController.getFtpWindowClient().getServerLabel().setText(mainController.getFtpWindowClient().getModel().getTextsList().get(2) + mainController.getMainClient().getLocalAddress());
                    mainController.addFtpClientEventListeners();
                }
                mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " - " + mainController.getCurrentUser().getUserType().getType().toUpperCase() + " open FTP explorer");
            }
            case "EMAIL" -> {
                mainController.setEmailView(new Email(mainController));
                Thread hilo = new Thread(mainController.getEmailView());
                hilo.start();
                //ReceiveEmail receiveEmail= new ReceiveEmail(mainController);
                //receiveEmail.check();
                mainController.addEmailListeners();
                mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " open EMAIL explorer");
            }
            case "CASOS" -> {
                mainController.setCasosView(new Casos(mainController));
                mainController.addCasosEventListeners();
                mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " open CASOS explorer");
            }
            case "ACERCA" -> {
                mainController.setAbout(new About(mainController));
                mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " open ABOUT window");
            }
            case "LOG OUT" -> {
                mainController.setLoginView(new Login(mainController.getLanguage()));
                mainController.addLoginEventListeners();
                mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " LOG OUT");
            }
        }
        mainController.getMainConnection().closeConnection();
    }
}
