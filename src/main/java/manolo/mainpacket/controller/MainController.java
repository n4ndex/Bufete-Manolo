package manolo.mainpacket.controller;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.model.viewmodels.Email;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.view.EmailTexts;
import manolo.mainpacket.view.Login;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.Register;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class MainController {
    MainConnectionModel mainConnectionModel;
    MainConnection mainConnection;
    MainViewModel mainViewModel;
    Login loginView;
    Register registerView;
    Menu menu;
    EmailTexts emailModel;
    FtpServiceModel ftpServiceModel;
    FtpService ftpService;
    FTPClient mainClient;

    public MainController() {
        initAttributes();
        addEventListeners();
    }

    private void showErrorWindow(Component parentComponent, String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, mainViewModel.getERROR(), JOptionPane.ERROR_MESSAGE);
    }

    private void initAttributes() {
        mainConnectionModel = new MainConnectionModel();
//        mainConnection = new MainConnection(mainConnectionModel.getDRIVER(), mainConnectionModel.getMYSQL_URL(), mainConnectionModel.getMYSQL_DATABASE(), mainConnectionModel.getMYSQL_USERNAME(), mainConnectionModel.getPASSWORD());
        mainViewModel = new MainViewModel();
        loginView = new Login();
        ftpServiceModel = new FtpServiceModel();
        ftpService = new FtpService();
    }

    private void addEventListeners() {
        addLoginEventListeners();
    }


    public void addLoginEventListeners() {
        loginView.getLabels().get(3).addMouseListener(new manolo.mainpacket.controller.listeners.login.LabelsListener(this));
        loginView.getButtons().getFirst().addActionListener(new manolo.mainpacket.controller.listeners.login.ButtonsListener(this));
    }

    public void submitLogin() {
        boolean ableToLogin = true;
        // TODO check the login
        if (ableToLogin) {
            loginView.dispose();
            menu = new Menu();
            addMenuEventListeners();
        } else {
            showErrorWindow(loginView, "Error al iniciar sesion");
        }
    }

    public void addRegisterEventListeners() {
        registerView.getLabels().get(4).addMouseListener(new manolo.mainpacket.controller.listeners.register.LabelsListener(this));
        registerView.getButtons().getFirst().addActionListener(new manolo.mainpacket.controller.listeners.register.ButtonsListener(this));
    }

    public void submitRegister() {
        boolean ableToRegister = true;
        // TODO check the register
        if (ableToRegister) {
            registerView.dispose();
            menu = new Menu();
            addMenuEventListeners();
        } else {
            showErrorWindow(registerView, "Error al registrarse");
        }
    }

    private void addMenuEventListeners() {
        for (int i = 0; i < menu.getButtons().size(); i++) {
            menu.getButtons().get(i).addActionListener(e -> {
                switch (((JButton) e.getSource()).getName()) {
                    case "FTP" -> System.out.println("FTP");
                    case "EMAIL" -> openEmail();
                }
            });
        }
    }

    private void openEmail() {
        boolean isCreated = false;
        if (isCreated == false) {
            emailModel = new EmailTexts();
            new Email(emailModel);
            isCreated = true;
        } else {
            System.out.println("Ya esta creado");
        }
    }
}
