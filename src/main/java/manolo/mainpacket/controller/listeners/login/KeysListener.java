
/**
 * KeysListener - KeyListener for handling key events in the login view of the MainPacket application.
 *
 * This class listens for key events, specifically Enter key presses, in the login view and triggers the login submission.
 */

package manolo.mainpacket.controller.listeners.login;

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
            mainController.submitLogin();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
