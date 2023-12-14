package manolo.mainpacket.controller;

import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.view.Login;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.Register;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MainController {
    MainConnectionModel mainConnectionModel;
    MainConnection mainConnection;
    MainViewModel mainViewModel;
    Login loginView;
    Register registerView;
    Menu menu;
    FtpServiceModel ftpServiceModel;
    FtpService ftpService;
    FTPClient mainClient;

    public MainController() {
        initAttributes();
        addEventListeners();
    }

    private void initAttributes() {
        mainConnectionModel = new MainConnectionModel();
//        mainConnection = new MainConnection(mainConnectionModel.getDRIVER(), mainConnectionModel.getMYSQL_URL(), mainConnectionModel.getMYSQL_DATABASE(), mainConnectionModel.getMYSQL_USERNAME(), mainConnectionModel.getPASSWORD());
        mainViewModel = new MainViewModel();
        loginView = new Login();
        registerView = new Register();
        menu = new Menu();
        ftpServiceModel = new FtpServiceModel();
        ftpService = new FtpService();
    }

    private void addEventListeners() {
        addLoginEventListeners();
        addRegisterEventListeners();
    }

    private void addLoginEventListeners() {
        loginView.getLabels().get(3).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginView.setVisible(false);
                registerView.setVisible(true);
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
            submitLogin();
        });
    }

    private void submitLogin() {
        boolean ableToLogin = false;
        // TODO check the login
        if (ableToLogin) {
            loginView.setVisible(false);
            menu.setVisible(true);
        } else {
            showErrorWindow(loginView, "Error al iniciar sesion");
        }
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
        registerView.getButtons().getFirst().addActionListener(e -> {
            submitRegister();
        });
    }

    private void submitRegister() {
    }

    private void closeConnections() {
        mainConnection.closeConnection();
        try {
            mainClient.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorWindow(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent, message, mainViewModel.getERROR(), JOptionPane.ERROR_MESSAGE);
    }
}
