package manolo.mainpacket.controller.listeners.menu;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.*;
import manolo.mainpacket.model.viewmodels.EmailTexts;

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
        mainController.getMenu().dispose();
        switch (((JButton) e.getSource()).getName()) {
            case "FTP" -> {
                if (isLawyer) {
                    mainController.setFtpWindow(new FTPWindow(mainController));
                    mainController.getFtpWindow().setLawyerDni(mainController.getCurrentUser().getDni());
                    mainController.getFtpWindow().loadDirectory(mainController.getMainClient(),mainController.getFtpWindow().getLawyerDni());
                    mainController.getFtpWindow().getServerLabel().setText(mainController.getFtpWindow().getModel().getTextsList().get(2) + mainController.getMainClient().getLocalAddress());
                    mainController.getFtpWindow().getDNILabel().setText(mainController.getFtpWindow().getModel().getTextsList().get(0) + mainController.getFtpWindow().getLawyerDni());
                    mainController.addFtpEventListeners();
                } else {
                    mainController.setFtpWindowClient(new FTPWindowClient(mainController));
                }
            }
            case "EMAIL" -> {
                mainController.setEmailModel(new EmailTexts());
                mainController.setEmailView(new Email(mainController.getEmailModel()));
                mainController.addEmailListeners();
            }
            case "CASOS" -> {
                mainController.setCasosView(new Casos(mainController));
            }
            case "LOG OUT" -> {
                mainController.setLoginView(new Login());
                mainController.addLoginEventListeners();
            }
        }
    }
}
