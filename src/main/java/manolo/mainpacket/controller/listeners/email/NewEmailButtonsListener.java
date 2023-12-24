package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.smptGmail.SendEmail;
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
        //BACK BUTTON
        if (e.getSource()==mainController.getNewEmail().getCancelButton()){
            mainController.getNewEmail().dispose();
            mainController.getEmailView().setVisible(true);
        }

        //SEND EMAIL BUTTON
        if (e.getSource()==mainController.getNewEmail().getSendButton()){
            SendEmail send= new SendEmail(mainController);
            send.createEmail(mainController.getNewEmail().getToTextfield(),
                             mainController.getNewEmail().getSubjetTextArea(),
                             mainController.getNewEmail().getMessageTextfield());
            send.sendEmail();
        }

    }
}
