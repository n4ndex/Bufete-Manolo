
/**
 * KeysListener - KeyListener for handling key events in the register view.
 *
 * This class responds to key events in the register view, specifically the Enter key, by triggering the registration process.
 */

package manolo.mainpacket.controller.listeners.register;

import manolo.mainpacket.controller.MainController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeysListener implements KeyListener {
    private final MainController mainController;

    public KeysListener(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            mainController.submitRegister();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
