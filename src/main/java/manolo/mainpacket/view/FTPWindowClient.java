package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.FTPTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class FTPWindowClient extends JFrame {
    // Swing components
    private JPanel mainPanel;         // Main panel containing FTPWindowClient components
    private JList listFiles;          // List component for displaying client files
    private JButton downloadButton;   // Button to download a file
    private JLabel serverLabel;        // Label for displaying server information
    private JLabel nameLabel;          // Label for displaying client name
    private MainController mainController;  // Reference to the MainController
    private String clientName = "";   // Client's name
    private FTPTexts model = new FTPTexts();  // Model containing texts for FTP client window

    // Constructor for FTPWindowClient, initializes components and loads client files.
    public FTPWindowClient(MainController mainController) {
        this.mainController = mainController;
        initComponents();     // Initialize Swing components
        settings();           // Apply settings and make the FTP client window visible
    }

    // Method to initialize Swing components.
    private void initComponents() {
        loadClientFiles();    // Load client-specific files into the list
        nameLabel.setText(model.getTextsList().get(0) + clientName);  // Set initial text for nameLabel
        serverLabel.setText(model.getTextsList().get(3));  // Set text for serverLabel
        downloadButton.setText(model.getTextsList().get(7));  // Set text for downloadButton

        // Set font and background color for downloadButton
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        downloadButton.setFont(buttonFont);
        downloadButton.setBackground(new Color(255, 215, 0));  // Set button background color to gold
    }

    // Method to load client-specific files into the list.
    public void loadClientFiles() {
        // Get client files from the FTP service
        String[] clientFiles = mainController.getFtpService().getClientFiles(mainController.getCurrentUser().getDni(), mainController.getMainClient());

        // Create a DefaultListModel to hold the client files
        DefaultListModel<String> listModel = new DefaultListModel<>();

        // Add client files to the list model
        for (String fileName : clientFiles) {
            listModel.addElement(fileName);
        }

        // Set the list model for the listFiles component
        listFiles.setModel(listModel);
    }

    // Method to initialize UI settings.
    private void settings() {
        this.setContentPane(mainPanel);
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        // Set window size, title, location, and other properties
        this.setSize(screenWidth / 3, screenHeight / 3);
        this.setTitle(model.getTitle());
        this.setLocationRelativeTo(mainController.getMenu());
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Add window listener to make the menu visible upon closing "FTP" window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                mainController.getMenu().setVisible(true);
            }
        });

        this.setVisible(true);  // Make the FTP client window visible
    }
}
