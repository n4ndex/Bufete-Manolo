package manolo.mainpacket.controller;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.view.Email;
import manolo.mainpacket.model.viewmodels.EmailTexts;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.*;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class MainController {
    private MainConnectionModel mainConnectionModel;
    private MainConnection mainConnection;
    private MainViewModel mainViewModel;
    private Login loginView;
    private Register registerView;
    private Menu menu;
    private FTPWindow ftpWindow;
    private Casos casosView;
    private EmailTexts emailModel;
    private Email emailView;
    private FtpServiceModel ftpServiceModel;
    private FtpService ftpService;
    private FTPClient mainClient;

    public MainController() {
        initAttributes();
        addEventListeners();
    }

    private void showErrorWindow(Component parentComponent, String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, mainViewModel.getERROR(), JOptionPane.ERROR_MESSAGE);
    }

    private void initAttributes() {
        mainConnectionModel = new MainConnectionModel();
        mainConnection = new MainConnection(mainConnectionModel.getDRIVER());
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
        ftpWindow.getExitButton().addActionListener(e -> {
            ftpWindow.dispose();
            menu.setVisible(true);
        });

        ftpWindow.getCreateDirButton().addActionListener(e -> {
            String folderName = JOptionPane.showInputDialog(ftpWindow, "Ingrese el nombre de la carpeta:");

            if (folderName != null && !folderName.isEmpty()) {
                String currentDirectoryPath = ftpWindow.getDirectory();
                ftpService.createDirectory(currentDirectoryPath, folderName, mainClient, ftpWindow);
                ftpWindow.loadDirectory(mainClient);
            } else {
                JOptionPane.showMessageDialog(ftpWindow, "Error al crear la carpeta.");
            }
        });
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
//        ftpWindow.getDeleteDirButton().addActionListener(e -> {
//            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ftpWindow.getTreeDirectories().getLastSelectedPathComponent();
//
//            if (selectedNode != null) {
//                File selectedDirectory = new File(ftpWindow.getDirectory() + File.separator + selectedNode.getUserObject());
//
//                if (selectedDirectory.exists() && selectedDirectory.isDirectory()) {
//                    if (ftpWindow.deleteDirectory(selectedDirectory)) {
//                        ftpWindow.loadDirectory(new File(ftpWindow.getDirectory()));
//                    } else {
//                        JOptionPane.showMessageDialog(ftpWindow, "Error al eliminar la carpeta.");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(ftpWindow, "Selecciona una carpeta válida para eliminar.");
//                }
//            } else {
//                JOptionPane.showMessageDialog(ftpWindow, "Selecciona una carpeta para eliminar.");
//            }
//        });
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
