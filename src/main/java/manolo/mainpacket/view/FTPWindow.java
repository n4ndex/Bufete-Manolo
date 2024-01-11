package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.viewmodels.FTPTexts;
import manolo.mainpacket.model.viewmodels.FTPTexts_en;
import manolo.mainpacket.model.viewmodels.FTPTexts_es;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class FTPWindow extends JFrame {
    // Swing components
    private JTextField renameField;        // Text field for renaming files or directories
    private JButton createDirButton;       // Button to create a new directory
    private JButton deleteFileButton;      // Button to delete a file
    private JButton uploadButton;          // Button to upload a file
    private JButton downloadButton;        // Button to download a file
    private JButton exitButton;            // Button to exit the FTP window
    private JButton deleteDirButton;       // Button to delete a directory
    private JButton refreshButton;         // Button to refresh the directory view
    private JTree treeDirectories;         // Tree component for displaying directory structure
    private JPanel mainPanel;              // Main panel containing FTPWindow components
    private JLabel userTypeLabel;          // Label to display the current directory path
    private JLabel nameLabel;              // Label for displaying user names
    private JLabel serverLabel;            // Label for displaying server information
    private JLabel renameLabel;            // Label for renaming files or directories
    private JButton renameButton;          // Button to perform file or directory renaming

    // FTP related attributes
    private String directory = "";         // Current working directory
    private String lawyerDni = "";         // Lawyer's DNI (identification)
    private String lawyerName = "";        // Lawyer's name
    private FTPClient ftpClient;           // FTP client for handling FTP operations
    private FtpService ftpService;         // Service class for FTP operations

    // Model for FTP texts
    private FTPTexts model;
    private FTPTexts_es modelEs = new FTPTexts_es();
    private FTPTexts_en modelEn = new FTPTexts_en();

    // Constructor for FTPWindow, initializes FTP-related components and settings.
    public FTPWindow(MainController mainController) {
        switchLanguage(mainController.getLanguage());
        this.ftpClient = mainController.getMainClient();
        this.ftpService = mainController.getFtpService();
        initComponents();           // Initialize Swing components
        settings(mainController);   // Apply settings and make the FTP client window visible
    }

    // Method for switch language of FTP Window
    private void switchLanguage(String language) {
        switch (language) {
            case "espanol":
                model = modelEs;
                break;
            case "english":
                model = modelEn;
                break;
        }
    }

    private void initComponents() {
        loadDirectory(ftpClient, lawyerDni);
        setLabelTexts();
        setButtonTexts();
        setButtonStyles();
    }

    private void setLabelTexts() {
        nameLabel.setText(model.getTextsList().get(0));
        userTypeLabel.setText(model.getTextsList().get(1));
        serverLabel.setText(model.getTextsList().get(2));
        renameLabel.setText(model.getTextsList().get(10));
    }

    private void setButtonTexts() {
        createDirButton.setText(model.getTextsList().get(3));
        deleteDirButton.setText(model.getTextsList().get(4));
        uploadButton.setText(model.getTextsList().get(5));
        deleteFileButton.setText(model.getTextsList().get(6));
        downloadButton.setText(model.getTextsList().get(7));
        exitButton.setText(model.getTextsList().get(8));
        refreshButton.setText(model.getTextsList().get(9));
        renameButton.setText(model.getTextsList().get(11));
    }

    private void setButtonStyles() {
        applyButtonStyle(createDirButton);
        applyButtonStyle(deleteFileButton);
        applyButtonStyle(uploadButton);
        applyButtonStyle(downloadButton);
        applyButtonStyle(deleteDirButton);
        applyButtonStyle(renameButton);
    }

    private void applyButtonStyle(JButton button) {
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        button.setFont(buttonFont);
        button.setBackground(new Color(255, 215, 0));  // Set button background color to gold
    }

    public DefaultMutableTreeNode getSelectedNode() {
        TreePath selectedPath = treeDirectories.getSelectionPath();
        if (selectedPath != null) {
            return (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        }
        return null;
    }

    public String getSelectedDirectoryPath(DefaultMutableTreeNode selectedNode) {
        while (selectedNode != null) {
            if (selectedNode.getUserObject() != null) {
                return selectedNode.getUserObject().toString();
            }
            selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
        }
        return "";
    }

    private DefaultMutableTreeNode createNodes(String path) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(path);
        FTPFile[] ftpFiles = ftpService.listFiles(path, ftpClient);

        if (ftpFiles != null) {
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.isDirectory()) {
                    rootNode.add(createNodes(path + "/" + ftpFile.getName()));
                } else {
                    rootNode.add(new DefaultMutableTreeNode(ftpFile.getName()));
                }
            }
        }

        return rootNode;
    }

    public void loadDirectory(FTPClient ftpClient, String path) {
        try {
            directory = ftpClient.printWorkingDirectory().substring(1);
            String fullPath = directory + "/" + path;
            loadDirectoryInternal(fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDirectoryInternal(String path) {
        try {
            DefaultMutableTreeNode rootNode = createNodes(path);

            treeDirectories.setModel(new DefaultTreeModel(rootNode));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to initialize UI settings.
    private void settings(MainController mainController) {
        ImageIcon icon = new ImageIcon(mainController.getMainViewModel().getICON_PATH());
        setIconImage(icon.getImage());
        this.setContentPane(mainPanel);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height + 300;
        int screenWidth = screen.width + 200;

        // Set window size, title, location, and other properties
        this.setTitle(model.getTitle());
        this.setSize(screenWidth / 2, screenHeight / 3);
        this.setLocationRelativeTo(mainController.getMenu());
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Add window listener to make the menu visible upon closing "FTP" window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                mainController.getMenu().setVisible(true);
            }
        });

        this.setVisible(true);
    }
}
