package manolo.mainpacket.controller.listeners.email;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.NewEmail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailListeners implements ActionListener {

    MainController controller;

    public  EmailListeners(MainController controller){
        this.controller=controller;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== controller.getEmail().getButtons().get(0)){
            controller.getEmail().dispose();
            controller.setNewEmail(new NewEmail());
        }

        if (e.getSource()== controller.getEmail().getButtons().get(1)){
            controller.getEmail().dispose();
            controller.setMenu(new Menu());
            controller.getMenu().setVisible(true);
            controller.addMenuEventListeners();
        }
    }
}
