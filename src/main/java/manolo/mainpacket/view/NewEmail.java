package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.EmailTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class NewEmail extends JFrame {
    // Swing components for the new email window
    private JButton sendButton;        // Button to send the email
    private JButton cancelButton;      // Button to cancel composing the email
    private JButton addFilesButton;    // Button to add files to the email
    private JLabel filesNameLabel;     // Label displaying the names of attached files
    private JLabel toTextLabel;        // Label displaying the to title
    private JTextField toTextfield;    // Textfield for entering the recipient's email address
    private JLabel subjectTextLabel;   // Label displaying the subject title
    private JTextArea subjetTextArea;  // Text area for entering the email subject
    private JPanel mainPanel;           // Main panel containing NewEmail components
    private JLabel messageTextLabel;    // Label displaying the message text title
    private JTextField messageTextfield;  // Textfield for composing the email message
    private JLabel filesAttachedLabel; // Textfielf shows the files attached

    // Model for email text
    private EmailTexts model;

    // Constructor for the NewEmail class, initializes components and applies settings.
    public NewEmail(MainController mainController) {
        this.model = mainController.getEmailModel();
        settings(mainController);  // Apply window settings
        initComponents();
    }

    // Method to initialize Swing components.
    private void initComponents() {
        sendButton.setText(model.getTexts().get(6));
        cancelButton.setText(model.getTexts().get(7));
        addFilesButton.setText(model.getTexts().get(8));
        toTextLabel.setText(model.getTexts().get(9));
        subjectTextLabel.setText(model.getTexts().get(10));
        messageTextLabel.setText(model.getTexts().get(11));
        filesAttachedLabel.setText(model.getTexts().get(12));
    }

    // Method to apply settings to the new email window.
    private void settings(MainController mainController) {
        // Set the content pane to the main panel
        this.setContentPane(mainPanel);

        // Set the window icon
        ImageIcon icon = new ImageIcon(mainController.getMainViewModel().getICON_PATH());
        setIconImage(icon.getImage());

        // Get screen dimensions
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        // Set window title, size, location, and other properties
        this.setTitle(mainController.getEmailModel().getTitle());
        this.setSize(screenWidth / 3, screenHeight / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                mainController.getEmailView().setVisible(true);
            }
        });
        this.setVisible(true);  // Make the new email window visible
    }


}
