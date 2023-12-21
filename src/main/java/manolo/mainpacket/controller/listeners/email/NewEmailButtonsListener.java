package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.Email;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEmailButtonsListener implements ActionListener {

    MainController mainController;

    public NewEmailButtonsListener(MainController mainController){
        this.mainController=mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==mainController.getNewEmail().getCancelButton()){
            mainController.getNewEmail().dispose();
            mainController.setEmailView(new Email(mainController.getEmailModel()));
            mainController.addEmailListeners();
        }

    }
}
