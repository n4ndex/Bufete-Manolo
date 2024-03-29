package manolo.mainpacket.controller.listeners.email;

import lombok.Getter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.smptGmail.SendEmail;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * NewEmailButtonsListener - ActionListener for handling button events in the New Email view of the MainPacket application.
 * This class listens for button click events in the New Email view and performs corresponding actions based on the clicked button, such as sending an email, attaching files, or canceling the email composition.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
@Getter
public class NewEmailButtonsListener implements ActionListener {
    MainController mainController;
    SendEmail send;

    public NewEmailButtonsListener(MainController mainController) {
        this.mainController = mainController;
        this.send = new SendEmail(mainController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //BACK BUTTON
        if (e.getSource() == mainController.getNewEmail().getCancelButton()) {
            mainController.getNewEmail().dispose();
            mainController.getEmailView().setVisible(true);
            mainController.getEmailView().setKeepChecking(true);
            Thread thread = new Thread(mainController.getEmailView());
            thread.start();
        }

        //SEND EMAIL BUTTON
        if (e.getSource() == mainController.getNewEmail().getSendButton()) {
            mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());

            send.createEmail(mainController.getNewEmail().getToTextfield(), mainController.getNewEmail().getSubjetTextArea(), mainController.getNewEmail().getMessageTextfield());
            send.sendEmail();

            mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), "EMAIL SEND to " + mainController.getNewEmail().getToTextfield().getText());
            mainController.getMainConnection().closeConnection();
        }

        // ADD FILES BUTTON
        if (e.getSource() == mainController.getNewEmail().getAddFilesButton()) {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (chooser.showOpenDialog(send) != JFileChooser.CANCEL_OPTION){
                send.setFiles(chooser.getSelectedFiles());

                for (File file: send.getFiles()){
                    send.setFilesNames(send.getFilesNames()+ file.getName()+ "<br>");
                }

                mainController.getNewEmail().getFilesAttachedLabel().setText("<html><p>" + send.getFilesNames() + "</p></html>");
            }
        }
    }
}
