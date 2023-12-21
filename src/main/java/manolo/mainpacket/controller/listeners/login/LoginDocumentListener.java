package manolo.mainpacket.controller.listeners.login;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import manolo.mainpacket.controller.MainController;

public class LoginDocumentListener implements DocumentListener {

    private MainController mainController;

    public LoginDocumentListener(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateLoginButtonState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateLoginButtonState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void updateLoginButtonState() {
        mainController.getLoginView().getButtons().getFirst().setEnabled(areFieldsFilled());
    }

    private boolean areFieldsFilled() {
        return !mainController.getLoginView().getTextFields().getFirst().getText().isEmpty()
                && mainController.getLoginView().getPasswordFields().getFirst().getPassword().length > 0;
    }
}
