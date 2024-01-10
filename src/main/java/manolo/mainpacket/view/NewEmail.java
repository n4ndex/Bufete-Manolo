package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class NewEmail extends JFrame {
    // Swing components for the new email window
    private JButton sendButton;        // Button to send the email
    private JButton cancelButton;      // Button to cancel composing the email
    private JButton addFilesButton;    // Button to add files to the email
    private JLabel filesNameLabel;     // Label displaying the names of attached files
    private JTextField toTextfield;    // Textfield for entering the recipient's email address
    private JTextArea subjetTextArea;  // Text area for entering the email subject
    private JPanel mainPanel;           // Main panel containing NewEmail components
    private JTextField messageTextfield;  // Textfield for composing the email message

    // Constructor for the NewEmail class, initializes components and applies settings.
    public NewEmail() {
        settings();  // Apply window settings
    }

    // Method to apply settings to the new email window.
    private void settings() {
        // Set the content pane to the main panel
        this.setContentPane(mainPanel);

        // Set the window icon
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());

        // Get screen dimensions
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        // Set window title, size, location, and other properties
        this.setTitle("New Email");
        this.setSize(screenWidth / 3, screenHeight / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);  // Make the new email window visible
    }
}
