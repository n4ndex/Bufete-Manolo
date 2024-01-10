package manolo.mainpacket.controller;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.databaseconnection.MainConnection;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.controller.listeners.email.EmailButtonsListener;
import manolo.mainpacket.controller.listeners.email.EmailLabelsListener;
import manolo.mainpacket.controller.listeners.email.NewEmailButtonsListener;
import manolo.mainpacket.controller.listeners.login.KeysListener;
import manolo.mainpacket.model.User;
import manolo.mainpacket.model.UserType;
import manolo.mainpacket.model.controllermodels.FtpServiceModel;
import manolo.mainpacket.model.controllermodels.MainConnectionModel;
import manolo.mainpacket.model.controllermodels.Utils;
import manolo.mainpacket.model.viewmodels.EmailTexts;
import manolo.mainpacket.model.viewmodels.MainViewModel;
import manolo.mainpacket.view.Menu;
import manolo.mainpacket.view.*;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Getter
@Setter
public class MainController {
    private User currentUser;
    private boolean isLawyer;
    private MainConnectionModel mainConnectionModel;
    private MainConnection mainConnection;
    private MainViewModel mainViewModel;
    private Login loginView;
    private Register registerView;
    private Menu menu;
    private FTPWindow ftpWindow;
    private FTPWindowClient ftpWindowClient;
    private Casos casosView;
    private EmailTexts emailModel;
    private Email emailView;
    private NewEmail newEmail;
    private About about;
    private FtpServiceModel ftpServiceModel;
    private FtpService ftpService;
    private FTPClient mainClient;

    public MainController() {
        initAttributes();
        addLoginEventListeners();
    }

    private void initAttributes() {
        mainConnectionModel = new MainConnectionModel();
        mainConnection = new MainConnection(mainConnectionModel.getDRIVER());
        mainViewModel = new MainViewModel();
        loginView = new Login();
        ftpServiceModel = new FtpServiceModel();
        ftpService = new FtpService(this);
        isLawyer = false;
    }

    public void addLoginEventListeners() {
        loginView.getLabels().get(3).addMouseListener(new manolo.mainpacket.controller.listeners.login.LabelsListener(this));
        loginView.getButtons().getFirst().addActionListener(new manolo.mainpacket.controller.listeners.login.ButtonsListener(this));
        loginView.getTextFields().getFirst().getDocument().addDocumentListener(new manolo.mainpacket.controller.listeners.login.LoginDocumentListener(this));
        loginView.getPasswordFields().getFirst().getDocument().addDocumentListener(new manolo.mainpacket.controller.listeners.login.LoginDocumentListener(this));
        loginView.getPasswordFields().getFirst().addKeyListener(new KeysListener(this));
    }

    public void addRegisterEventListeners() {
        registerView.getLabels().get(5).addMouseListener(new manolo.mainpacket.controller.listeners.register.LabelsListener(this));
        registerView.getButtons().getFirst().addActionListener(new manolo.mainpacket.controller.listeners.register.ButtonsListener(this));
        registerView.getTextFields().getFirst().getDocument().addDocumentListener(new manolo.mainpacket.controller.listeners.register.RegisterDocumentListener(this));
        registerView.getTextFields().get(1).getDocument().addDocumentListener(new manolo.mainpacket.controller.listeners.register.RegisterDocumentListener(this));
        registerView.getTextFields().get(2).getDocument().addDocumentListener(new manolo.mainpacket.controller.listeners.register.RegisterDocumentListener(this));
        registerView.getPasswordFields().getFirst().getDocument().addDocumentListener(new manolo.mainpacket.controller.listeners.register.RegisterDocumentListener(this));
        registerView.getPasswordFields().getFirst().addKeyListener(new manolo.mainpacket.controller.listeners.register.KeysListener(this));
    }

    public void addMenuEventListeners() {
        for (int i = 0; i < menu.getButtons().size(); i++) {
            menu.getButtons().get(i).addActionListener(new manolo.mainpacket.controller.listeners.menu.ButtonsListener(this, isLawyer));
        }
    }

    public void addFtpLawyerEventListeners() {
        ftpWindow.getTreeDirectories().addTreeSelectionListener(new manolo.mainpacket.controller.listeners.ftp.TreeListener(this));
        ftpWindow.getExitButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getCreateDirButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getDeleteDirButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getDeleteFileButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getDownloadButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getUploadButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getRefreshButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getRenameButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ButtonsListener(this));
        ftpWindow.getRenameField().addKeyListener(new manolo.mainpacket.controller.listeners.ftp.KeysListener(this));
    }

    public void addFtpClientEventListeners() {
        ftpWindowClient.getDownloadButton().addActionListener(new manolo.mainpacket.controller.listeners.ftp.ClientDownloadListener(this));
    }

    public void addEmailListeners() {
        for (int i = 0; i < emailView.getButtons().size(); i++) {
            emailView.getButtons().get(i).addActionListener(new EmailButtonsListener(this));
        }
        for (int i = 0; i < emailView.getLabels().size(); i++) {
            emailView.getLabels().get(i).addMouseListener(new EmailLabelsListener(this));
        }
    }

    public void addNewEmailListeners() {
        newEmail.getSendButton().addActionListener(new NewEmailButtonsListener(this));
        newEmail.getCancelButton().addActionListener(new NewEmailButtonsListener(this));
        newEmail.getAddFilesButton().addActionListener(new NewEmailButtonsListener(this));
    }

    public void addCasosEventListeners() {
        casosView.getButtonCrear().addActionListener(new manolo.mainpacket.controller.listeners.casos.ButtonsListener(this));
        casosView.getTextFieldCaso().addKeyListener(new manolo.mainpacket.controller.listeners.casos.KeysListener(this));
    }

    public void submitLogin() {
        mainConnection.openConnection(mainConnectionModel.getMYSQL_URL(), mainConnectionModel.getMYSQL_DATABASE(), mainConnectionModel.getMYSQL_USERNAME(), mainConnectionModel.getPASSWORD());

        String dni = loginView.getTextFields().getFirst().getText();
        String password = loginView.getPasswordFields().getFirst().getText();
        User userData = mainConnection.getUserData(dni, password);

        if (userData != null) {
            currentUser = userData;
            loginView.dispose();
            menu = new Menu();
            menu.setTitle("¡Bienvenido " + currentUser.getName() + "! - " + currentUser.getUserType().getType().toUpperCase());

            if (currentUser.getUserType().getType().equalsIgnoreCase("lawyer")) {
                menu.getButtons().get(2).setEnabled(true);
                mainClient = ftpService.loginFtp(ftpServiceModel.getHost(), ftpServiceModel.getPort(), ftpServiceModel.getUsernameLawyer(), ftpServiceModel.getPassword());
                isLawyer = true;
            } else {
                menu.getButtons().get(2).setEnabled(false);
                mainClient = ftpService.loginFtp(ftpServiceModel.getHost(), ftpServiceModel.getPort(), ftpServiceModel.getUsernameClient(), ftpServiceModel.getPassword());
                isLawyer = false;
            }

            mainConnection.insertLog(currentUser.getDni(), "LOGIN from user: " + currentUser.getName());

            addMenuEventListeners();
        } else {
            Utils.showErrorWindow(loginView, mainViewModel.getLOGIN_ERROR(), mainViewModel.getERROR());
        }

        mainConnection.closeConnection();
    }

    public void submitRegister() {
        mainConnection.openConnection(mainConnectionModel.getMYSQL_URL(), mainConnectionModel.getMYSQL_DATABASE(), mainConnectionModel.getMYSQL_USERNAME(), mainConnectionModel.getPASSWORD());

        String dni = registerView.getTextFields().get(0).getText();
        boolean userExists = mainConnection.checkIfUserExists(dni);

        if (areFieldsFilled() && isValidEmail() && isStrongPassword()) {
            if (isValidDNI(registerView.getTextFields().get(0).getText())) {
                if (!userExists) {
                    registerView.dispose();
                    menu = new Menu();

                    String name = registerView.getTextFields().get(1).getText();
                    String password = new String(registerView.getPasswordFields().getFirst().getPassword());
                    String email = registerView.getTextFields().get(2).getText();

                    String selectedLawyerName = registerView.getCombos().getFirst().getSelectedItem().toString();
                    int idLawyer = mainConnection.getIdFromName(selectedLawyerName);

                    isLawyer = registerView.getChecks().getFirst().isSelected();
                    UserType userType;

                    if (isLawyer) {
                        userType = new UserType(registerView.getModel().getUser_types().getFirst(), 0);
                        menu.getButtons().get(2).setEnabled(true);
                        mainClient = ftpService.loginFtp(ftpServiceModel.getHost(), ftpServiceModel.getPort(), ftpServiceModel.getUsernameLawyer(), ftpServiceModel.getPassword());

                        currentUser = new User(dni, name, password, email, userType, idLawyer);

                        String userFolderName = currentUser.getDni();
                        String userFolderPath = File.separator + userFolderName;

                        ftpService.createDirectory(userFolderPath, mainClient, ftpWindow);

                        String emptyFileName = "empty_file.txt";
                        String emptyFilePath = userFolderPath + File.separator + emptyFileName;
                        ftpService.createEmptyFile(emptyFilePath, mainClient);
                    } else {
                        userType = new UserType(registerView.getModel().getUser_types().get(1), 1);
                        menu.getButtons().get(2).setEnabled(false);
                        mainClient = ftpService.loginFtp(ftpServiceModel.getHost(), ftpServiceModel.getPort(), ftpServiceModel.getUsernameClient(), ftpServiceModel.getPassword());
                        currentUser = new User(dni, name, password, email, userType, idLawyer);
                    }

                    menu.setTitle("¡Bienvenido " + currentUser.getName() + "! - " + currentUser.getUserType().getType().toUpperCase());
                    mainConnection.insertNewUser(currentUser);

                    mainConnection.insertLog(currentUser.getDni(), "REGISTER & LOGIN from user: " + currentUser.getName());

                    addMenuEventListeners();

                    Utils.showInfoWindow(registerView, mainViewModel.getUSER_CREATED_SUCCESS(), mainViewModel.getINFO());
                } else {
                    Utils.showWarningWindow(registerView, mainViewModel.getUSER_EXISTS(), mainViewModel.getWARNING());
                }
            } else {
                Utils.showWarningWindow(registerView, mainViewModel.getINVALID_DNI(), mainViewModel.getWARNING());
            }
        }

        mainConnection.closeConnection();
    }

    private boolean areFieldsFilled() {
        for (JTextField textField : registerView.getTextFields())
            if (textField.getText().isEmpty()) return false;

        for (JPasswordField passwordField : registerView.getPasswordFields())
            if (new String(passwordField.getPassword()).isEmpty()) return false;

        // Comprobar si se ha seleccionado un elemento en el JComboBox
        JComboBox combo = registerView.getCombos().getFirst();
        return combo.getSelectedIndex() != -1;
    }

    public boolean isValidDNI(String dni) {
        String dniRegex = "^[0-9]{8}[A-Za-z]$";

        if (dni.matches(dniRegex)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidEmail() {
        String email = registerView.getTextFields().get(2).getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (email.matches(emailRegex)) {
            return true;
        } else {
            Utils.showWarningWindow(registerView, mainViewModel.getINVALID_EMAIL(), mainViewModel.getWARNING());
            return false;
        }
    }

    private boolean isStrongPassword() {
        String password = new String(registerView.getPasswordFields().getFirst().getPassword());
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (password.matches(passwordRegex)) {
            return true;
        } else {
            Utils.showWarningWindow(registerView, mainViewModel.getINVALID_PASSWORD(), mainViewModel.getWARNING());
            return false;
        }
    }
}
