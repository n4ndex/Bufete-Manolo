
/**
 * LabelsListener - MouseListener for handling mouse events on labels in the register view.
 *
 * This class responds to mouse events, specifically mouse clicks, on labels in the register view by switching to the login view.
 */

package manolo.mainpacket.controller.listeners.register;

import manolo.mainpacket.controller.MainController;

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
        mainController.getRegisterView().setVisible(false);
        mainController.getLoginView().setVisible(true);
        mainController.addLoginEventListeners();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
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
