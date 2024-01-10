package manolo.mainpacket.controller.listeners.login;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsListener implements ActionListener {
    private MainController mainController;

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
        Object source = e.getSource();

        if (source == mainController.getLoginView().getButtons().getFirst()) {
            mainController.submitLogin();
        } else if (source == mainController.getLoginView().getLanguageComboBox()) {
            JComboBox<Object> languageComboBox = mainController.getLoginView().getLanguageComboBox();
            int selectedIndex = languageComboBox.getSelectedIndex();

            if (selectedIndex == 0) {
                mainController.setLanguage("espanol");
            } else if (selectedIndex == 1) {
                mainController.setLanguage("english");
            }

            refreshLoginPage();
        }
    }

    private void refreshLoginPage() {
        mainController.getLoginView().dispose();

        mainController.setLoginView(new Login(mainController.getLanguage()));
        mainController.addLoginEventListeners();

        mainController.getLoginView().setVisible(true);
    }

}
