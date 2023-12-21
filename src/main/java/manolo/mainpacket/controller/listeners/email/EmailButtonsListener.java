package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.Email;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.NewEmail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailButtonsListener implements ActionListener {

    MainController mainController;

    public EmailButtonsListener(MainController controller){
        this.mainController =controller;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== mainController.getEmailView().getButtons().get(0)){
            mainController.getEmailView().dispose();
            mainController.setNewEmail(new NewEmail());
            mainController.addNewEmailListeners();
        }

        if (e.getSource()== mainController.getEmailView().getButtons().get(1)){
            mainController.getEmailView().dispose();
            mainController.setMenu(new Menu());
            mainController.getMenu().setVisible(true);
            mainController.addMenuEventListeners();
        }
    }
}
