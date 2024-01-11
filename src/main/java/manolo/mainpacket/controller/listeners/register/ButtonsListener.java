
/**
 * ButtonsListener - ActionListener for handling button clicks in the register view.
 *
 * This class responds to button clicks in the register view by triggering the registration process.
 */

package manolo.mainpacket.controller.listeners.register;

import manolo.mainpacket.controller.MainController;

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
        mainController.submitRegister();
    }
}
