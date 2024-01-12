package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.smptGmail.ReceiveEmail;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * EmailLabelsListener - MouseListener for handling mouse click events on labels in the EmailView of the MainPacket application.
 * This class listens for mouse click events on specific labels in the EmailView and performs corresponding actions based on the clicked label, such as printing a message to the console.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
public class EmailLabelsListener implements MouseListener {
    MainController mainController;
    private ReceiveEmail receiveEmail;

    public EmailLabelsListener(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == mainController.getEmailView().getLabels().get(0)) {
            receiveEmail = new ReceiveEmail(mainController,"INBOX");
            System.out.println("siBandeja");
        }
        if (e.getSource() == mainController.getEmailView().getLabels().get(1)) {
            mainController.getEmailView().setReceiveEmail(new ReceiveEmail(mainController,"[Gmail]/Spam"));
            System.out.println("siSpam");
        }
        if (e.getSource() == mainController.getEmailView().getLabels().get(2)) {
            mainController.getEmailView().setReceiveEmail(new ReceiveEmail(mainController,"[Gmail]/Enviados"));
            System.out.println("siEnviados");
        }
        if (e.getSource() == mainController.getEmailView().getLabels().get(3)) {
            mainController.getEmailView().setReceiveEmail(new ReceiveEmail(mainController,"[Gmail]/Borradores"));
            System.out.println("siBorradores");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
