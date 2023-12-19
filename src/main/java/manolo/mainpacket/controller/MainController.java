package manolo.mainpacket.controller;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.model.viewmodels.Email;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.*;
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
    FTPWindow ftpWindow;
    Casos casosView;
    EmailTexts emailModel;
    Email emailView;
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
        // mainConnection = new MainConnection(mainConnectionModel.getDRIVER(), mainConnectionModel.getMYSQL_URL(), mainConnectionModel.getMYSQL_DATABASE(), mainConnectionModel.getMYSQL_USERNAME(), mainConnectionModel.getPASSWORD());
        mainViewModel = new MainViewModel();
        loginView = new Login();
        ftpServiceModel = new FtpServiceModel();
        ftpService = new FtpService();
        // TODO traer de modelo
        mainClient = ftpService.loginFtp("127.0.0.1", 21, "root", "");
    }

    private void addEventListeners() {
        addLoginEventListeners();
    }


    public void addLoginEventListeners() {
        loginView.getLabels().get(3).addMouseListener(new manolo.mainpacket.controller.listeners.login.LabelsListener(this));
        loginView.getButtons().getFirst().addActionListener(new manolo.mainpacket.controller.listeners.login.ButtonsListener(this));
    }

    public void addRegisterEventListeners() {
        registerView.getLabels().get(4).addMouseListener(new manolo.mainpacket.controller.listeners.register.LabelsListener(this));
        registerView.getButtons().getFirst().addActionListener(new manolo.mainpacket.controller.listeners.register.ButtonsListener(this));
    }

    private void addMenuEventListeners() {
        for (int i = 0; i < menu.getButtons().size(); i++) {
            menu.getButtons().get(i).addActionListener(new manolo.mainpacket.controller.listeners.menu.ButtonsListener(this));
        }
    }

    public void addFtpEventListeners() {
        ftpWindow.getTreeDirectories().addTreeSelectionListener(new manolo.mainpacket.controller.listeners.ftp.TreeListener(this));
        ftpWindow.getExitButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getCreateDirButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getDeleteDirButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getDeleteFileButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getDownloadButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getUploadButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getRefreshButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getRenameField().addKeyListener(new manolo.mainpacket.controller.listeners.ftp.KeysListener(this));
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

//
//        ftpWindow.getDeleteFileButton().addActionListener(e -> {
//            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ftpWindow.getTreeDirectories().getLastSelectedPathComponent();
//
//            if (selectedNode != null) {
//                File selectedFile = new File(ftpWindow.getDirectory() + File.separator + ftpWindow.getFullPath(selectedNode));
//
//                if (selectedFile.exists() && selectedFile.isFile()) {
//                    if (ftpWindow.deleteFile(selectedFile)) {
//                        ftpWindow.loadDirectory(new File(ftpWindow.getDirectory()));
//                    } else {
//                        JOptionPane.showMessageDialog(ftpWindow, "Error al eliminar el archivo.");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo válido para eliminar.");
//                }
//            } else {
//                JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo para eliminar.");
//            }
//        });
//
//        ftpWindow.getDownloadButton().addActionListener(e -> {
//            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ftpWindow.getTreeDirectories().getLastSelectedPathComponent();
//
//            if (selectedNode != null) {
//                File selectedFile = new File(ftpWindow.getDirectory() + File.separator + ftpWindow.getFullPath(selectedNode));
//
//                if (selectedFile.exists() && selectedFile.isFile()) {
//                    ftpWindow.saveAs(selectedFile);
//                } else {
//                    JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo válido para descargar.");
//                }
//            } else {
//                JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo para descargar.");
//            }
//        });
//
//        ftpWindow.getUploadButton().addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Seleccionar Archivo para Subir");
//
//            int userSelection = fileChooser.showOpenDialog(ftpWindow);
//
//            if (userSelection == JFileChooser.APPROVE_OPTION) {
//                File selectedFile = fileChooser.getSelectedFile();
//                ftpWindow.uploadFile(selectedFile);
//            }
//        });
}
