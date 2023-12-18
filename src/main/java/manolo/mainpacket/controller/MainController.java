package manolo.mainpacket.controller;

import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.view.*;
import manolo.mainpacket.view.Menu;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class MainController{
    MainConnectionModel mainConnectionModel;
    MainConnection mainConnection;
    MainViewModel mainViewModel;
    Login loginView;
    Register registerView;
    Casos casosView;
    Menu menu;
    FtpServiceModel ftpServiceModel;
    FtpService ftpService;
    FTPClient mainClient;
    FTPWindow ftpWindow;

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
        casosView = new Casos();
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

    private void addMenuEventListeners() {
        for (int i = 0; i < menu.getButtons().size(); i++) {
            menu.getButtons().get(i).addActionListener(e -> {
                switch (((JButton)e.getSource()).getName()){
                    case "FTP":
                        ftpWindow = new FTPWindow();
                        ftpWindow.setVisible(true);
                        menu.setVisible(false);
                        addFTPEventListeners();
                }
            });
        }
    }

    private void addFTPEventListeners() {
        ftpWindow.getExitButton().addActionListener(e -> {
            ftpWindow.dispose();
            menu.setVisible(true);
        });

        ftpWindow.getCreateDirButton().addActionListener(e -> {
            String folderName = JOptionPane.showInputDialog(ftpWindow, "Ingrese el nombre de la carpeta:");

            if (folderName != null && !folderName.isEmpty()) {
                String currentDirectoryPath = ftpWindow.getDirectory();

                String newDirectoryPath = currentDirectoryPath + File.separator + folderName;

                File currentDirectory = new File(currentDirectoryPath);
                File newDirectory = new File(newDirectoryPath);
                if (newDirectory.mkdirs()) {
                    ftpWindow.loadDirectory(currentDirectory);
                } else {
                    JOptionPane.showMessageDialog(ftpWindow, "Error al crear la carpeta.");
                }
            }
        });

        ftpWindow.getDeleteDirButton().addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ftpWindow.getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                File selectedDirectory = new File(ftpWindow.getDirectory() + File.separator + selectedNode.getUserObject());

                if (selectedDirectory.exists() && selectedDirectory.isDirectory()) {
                    if (ftpWindow.deleteDirectory(selectedDirectory)) {
                        ftpWindow.loadDirectory(new File(ftpWindow.getDirectory()));
                    } else {
                        JOptionPane.showMessageDialog(ftpWindow, "Error al eliminar la carpeta.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ftpWindow, "Selecciona una carpeta válida para eliminar.");
                }
            } else {
                JOptionPane.showMessageDialog(ftpWindow, "Selecciona una carpeta para eliminar.");
            }
        });

        ftpWindow.getDeleteFileButton().addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ftpWindow.getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                File selectedFile = new File(ftpWindow.getDirectory() + File.separator + ftpWindow.getFullPath(selectedNode));

                if (selectedFile.exists() && selectedFile.isFile()) {
                    if (ftpWindow.deleteFile(selectedFile)) {
                        ftpWindow.loadDirectory(new File(ftpWindow.getDirectory()));
                    } else {
                        JOptionPane.showMessageDialog(ftpWindow, "Error al eliminar el archivo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo válido para eliminar.");
                }
            } else {
                JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo para eliminar.");
            }
        });

        ftpWindow.getDownloadButton().addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ftpWindow.getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                File selectedFile = new File(ftpWindow.getDirectory() + File.separator + ftpWindow.getFullPath(selectedNode));

                if (selectedFile.exists() && selectedFile.isFile()) {
                    ftpWindow.saveAs(selectedFile);
                } else {
                    JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo válido para descargar.");
                }
            } else {
                JOptionPane.showMessageDialog(ftpWindow, "Selecciona un archivo para descargar.");
            }
        });

        ftpWindow.getUploadButton().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar Archivo para Subir");

            int userSelection = fileChooser.showOpenDialog(ftpWindow);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ftpWindow.uploadFile(selectedFile);
            }
        });
    }

}


