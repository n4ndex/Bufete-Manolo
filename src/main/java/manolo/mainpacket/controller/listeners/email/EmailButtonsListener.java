package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.NewEmail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * EmailButtonsListener - ActionListener for handling button events related to email management in the MainPacket application.
 * This class listens for button events in the EmailView, specifically the "New Email" and "Back" buttons, and performs corresponding actions such as displaying the NewEmail view or navigating back to the main menu.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
public class EmailButtonsListener implements ActionListener {
    MainController mainController;

    public EmailButtonsListener(MainController controller) {
        this.mainController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //NEW EMAIL BUTTON
        if (e.getSource() == mainController.getEmailView().getButtons().get(0)) {
            mainController.getEmailView().dispose();
            mainController.setNewEmail(new NewEmail(mainController));
            mainController.addNewEmailListeners();
            mainController.getEmailView().setKeepChecking(false);
        }
        //BACK BUTTON
        if (e.getSource() == mainController.getEmailView().getButtons().get(1)) {
            mainController.getEmailView().dispose();
            mainController.getMenu().setVisible(true);
            mainController.getEmailView().setKeepChecking(false);
        }
    }
}
