package manolo.mainpacket.controller.listeners.menu;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.Email;
import manolo.mainpacket.model.viewmodels.EmailTexts;
import manolo.mainpacket.view.Casos;
import manolo.mainpacket.view.FTPWindow;
import manolo.mainpacket.view.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        mainController.getMenu().dispose();
        switch (((JButton) e.getSource()).getName()) {
            case "FTP" -> {
                mainController.setFtpWindow(new FTPWindow(mainController));
                mainController.getFtpWindow().getServerLabel().setText(mainController.getFtpWindow().getModel().getTextsList().get(2) + mainController.getMainClient().getLocalAddress());
                mainController.addFtpEventListeners();
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
