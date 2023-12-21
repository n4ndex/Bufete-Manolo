package manolo.mainpacket.controller.listeners.register;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import manolo.mainpacket.controller.MainController;

public class RegisterDocumentListener implements DocumentListener {

    private MainController mainController;

    public RegisterDocumentListener(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateRegisterButtonState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateRegisterButtonState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void updateRegisterButtonState() {
        mainController.getRegisterView().getButtons().getFirst().setEnabled(areFieldsFilled());
    }

    private boolean areFieldsFilled() {
        for (JTextField textField : mainController.getRegisterView().getTextFields())
            if (textField.getText().isEmpty()) return false;

        for (JPasswordField passwordField : mainController.getRegisterView().getPasswordFields())
            if (new String(passwordField.getPassword()).isEmpty()) return false;

        // Comprobar si se ha seleccionado un elemento en el JComboBox
        JComboBox combo = mainController.getRegisterView().getCombos().getFirst();
        return combo.getSelectedIndex() != -1;
    }
}
