package manolo.mainpacket.controller;

import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.view.Login;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.Register;
import org.apache.commons.net.ftp.FTPClient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainController {
    MainConnectionModel mainConnectionModel;
    MainConnection mainConnection;
    Register registerView;
    Login loginView;
    Menu menu;
    FtpServiceModel ftpServiceModel;
    FtpService ftpService;
    FTPClient mainClient;

    public MainController() {
        initAttributes();
        addEventListeners();
    }

    private void initAttributes() {
        // mainConnection = new MainConnection();
        registerView = new Register();
        loginView = new Login();
        menu = new Menu();
        ftpServiceModel = new FtpServiceModel();
        ftpService = new FtpService();
    }

    private void addEventListeners() {
        addLoginEventListeners();
    }

    private void addRegisterEventListeners() {
        registerView.getLabels().get(4).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerView.setVisible(false);
                loginView.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    private void addLoginEventListeners() {
        loginView.getLabels().get(3).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginView.setVisible(false);
                registerView.setVisible(true);
                addRegisterEventListeners();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        loginView.getButtons().getFirst().addActionListener(e -> {
            String dni = loginView.getTextFields().get(0).toString(); // getNickname
            String password = loginView.getPasswordFields().get(0).toString(); // getPassword
            if (dni != null || dni != "") {
                if (password != null || password != "") {
                    submitLogin(dni); // Compare SQL users
                } else {
                    System.out.println("La contraseña no puede estar vacia");
                }
            } else {
                System.out.println("El DNI no puede estar vacio");
            }
        });
    }

    private void submitLogin(String dni) {
        /*
        if(dni == rs.getNext()) {

        }
         */
        menu.setVisible(true);
        loginView.setVisible(false);
        addMenuEventListener();
    }

    private void addMenuEventListener() {

    }
}
