package manolo.mainpacket.controller;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.controller.listeners.EmailListeners;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.view.*;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.model.viewmodels.EmailTexts;
import manolo.mainpacket.view.Menu;
import org.apache.commons.net.ftp.FTPClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Getter
@Setter
public class MainController {
    MainConnectionModel mainConnectionModel;
    MainConnection mainConnection;
    MainViewModel mainViewModel;
    Login loginView;
    Register registerView;
    Menu menu;
    Email email;
    EmailTexts emailModel;
    NewEmail newEmail;
    FtpServiceModel ftpServiceModel;
    FtpService ftpService;
    FTPClient mainClient;

    public MainController() {
        initAttributes();
        addEventListeners();
        menu.setVisible(true);
    }

    private void showErrorWindow(Component parentComponent, String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, mainViewModel.getERROR(), JOptionPane.ERROR_MESSAGE);
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
        addMenuEventListeners();
    }


    private void addLoginEventListeners() {
        loginView.getLabels().get(3).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerView.setVisible(true);
                loginView.setVisible(false);
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

    public void addMenuEventListeners() {
        for (int i = 0; i < menu.getButtons().size(); i++) {
            menu.getButtons().get(i).addActionListener(e -> {
                switch (((JButton)e.getSource()).getName()){
                    case "FTP"-> System.out.println("FTP");
                    case "EMAIL"-> openEmail();
                }
            });
        }
    }

    private void openEmail() {
        menu.dispose();
        emailModel= new EmailTexts();
        email= new Email(emailModel);
        addEmailListeners(email);
    }

    public void addEmailListeners(Email email){
        for (int i=0; i<email.getButtons().size(); i++){
            email.getButtons().get(i).addActionListener(new EmailListeners(this));
        }
    }
}
