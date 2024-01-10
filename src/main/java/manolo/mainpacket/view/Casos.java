package manolo.mainpacket.view;

import lombok.Getter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.CasosTexts;
import manolo.mainpacket.model.viewmodels.CasosTexts_en;
import manolo.mainpacket.model.viewmodels.CasosTexts_es;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

@Getter
public class Casos extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitle;
    private JComboBox<String> comboClient;
    private JTextField textFieldCaso;
    private JButton buttonCrear;
    private JLabel labelClient;
    private JLabel labelCase;

    // Model for Casos texts
    private CasosTexts model;
    private CasosTexts_es modelEs = new CasosTexts_es();
    private CasosTexts_en modelEn = new CasosTexts_en();

    public Casos(MainController mainController) {
        switchLanguage(mainController.getLanguage());
        settings(mainController); // Initialize the frame settings
        initComponents(mainController); // Initialize Swing components
    }

    // Method for switch language of Casos Window
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

    private void initComponents(MainController mainController) {
        setLabelTexts(); // Set text for labels
        setButtonTexts(); // Set text for buttons
        createClientCombo(mainController); // Client names in the combo box
    }

    private void createClientCombo(MainController mainController) {
        ArrayList<String> clientNames = mainController.getMainConnection().getClientNames();

        for (String clientName : clientNames) {
            comboClient.addItem(clientName);
        }
    }

    private void setButtonTexts() {
        buttonCrear.setText(model.getTextsList().get(3)); // Set text for the "Crear" button

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        buttonCrear.setFont(buttonFont);
        buttonCrear.setBackground(new Color(255, 215, 0));  // Set button background color to gold
    }

    private void setLabelTexts() {
        labelTitle.setText(model.getTextsList().get(0)); // Set text for the title label
        labelClient.setText(model.getTextsList().get(1)); // Set text for the client label
        labelCase.setText(model.getTextsList().get(2)); // Set text for the case label
    }

    private void settings(MainController mainController) {
        this.setContentPane(mainPanel); // Set the content pane to the main panel
        ImageIcon icon = new ImageIcon(mainController.getMainViewModel().getICON_PATH());
        setIconImage(icon.getImage());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        this.setSize(screenWidth / 3, screenHeight / 3); // Set the size of the frame
        this.setTitle(model.getTitle()); // Set the title of the frame
        this.setLocationRelativeTo(mainController.getMenu()); // Set the location relative to the menu frame
        this.setResizable(false); // Make the frame not resizable
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Dispose the frame on close
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                mainController.getMenu().setVisible(true); // Make the menu visible when the "Casos" window is closed
            }
        });
        this.setVisible(true); // Set the frame to be visible
    }
}
