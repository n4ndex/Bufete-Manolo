
/**
 * EmailLabelsListener - MouseListener for handling mouse click events on labels in the EmailView of the MainPacket application.
 *
 * This class listens for mouse click events on specific labels in the EmailView and performs corresponding actions based
 * on the clicked label, such as printing a message to the console.
 */

package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EmailLabelsListener implements MouseListener {

    MainController mainController;

    public EmailLabelsListener(MainController mainController){
        this.mainController=mainController;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
            if (e.getSource()==mainController.getEmailView().getLabels().get(0)){
                System.out.println("si1");
            }
            if (e.getSource()==mainController.getEmailView().getLabels().get(1)){
                System.out.println("si2");
            }
            if (e.getSource()==mainController.getEmailView().getLabels().get(2)){
                System.out.println("si3");
            }
            if (e.getSource()==mainController.getEmailView().getLabels().get(3)){
                System.out.println("si4");
            }
    }


    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
