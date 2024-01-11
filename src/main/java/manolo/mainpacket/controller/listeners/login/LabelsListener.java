
/**
 * LabelsListener - MouseListener for handling label click events in the login view of the MainPacket application.
 *
 * This class listens for mouse events on specific labels and triggers actions accordingly.
 */

package manolo.mainpacket.controller.listeners.login;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.Register;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelsListener implements MouseListener {
    private final MainController mainController;

    public LabelsListener(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        mainController.getLoginView().dispose();
        mainController.setRegisterView(new Register(mainController, mainController.getLanguage()));
        mainController.addRegisterEventListeners();

        // Deshabilitar temporalmente el botón para evitar eventos duplicados
        mainController.getLoginView().getLabels().get(3).removeMouseListener(this);
    }


    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
