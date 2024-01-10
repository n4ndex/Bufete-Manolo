package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.AboutTexts;
import manolo.mainpacket.model.viewmodels.AboutTexts_en;
import manolo.mainpacket.model.viewmodels.AboutTexts_es;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

@Getter
@Setter
public class About extends JFrame {
    // Constants for the number of panels and labels
    private final int AMOUNT_PANELS = 5;
    private final int AMOUNT_LABELS = 8;

    // JFrame reference to the main menu
    private JFrame menu;

    // ArrayList to store panels
    private ArrayList<JPanel> panels = new ArrayList<>();

    // Model to get about texts
    private AboutTexts model;
    private AboutTexts_es modelEs = new AboutTexts_es();
    private AboutTexts_en modelEn = new AboutTexts_en();

    // Constructor that takes the menu JFrame as a parameter
    public About(MainController mainController) {
        // Assign the main menu to the local variable
        this.menu = mainController.getMenu();

        switchLanguage(mainController.getLanguage());

        // Set up the frame settings
        settings(mainController);

        // Create panels and set up the layout
        createPanels();
        addTitle();
        addContent();
        addImagePanel();
        combinePanels();

        // Add window listener to show the menu when the About window is closed
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                menu.setVisible(true);
            }
        });

        // Set the frame to be visible
        setVisible(true);
    }

    // Method for switch language of About Window
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

    // Method to combine and set layouts for specific panels
    private void combinePanels() {
        panels.get(3).setLayout(new GridLayout(0, 1, 10, 20));
        panels.get(4).setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        panels.get(4).add(panels.get(3));
        panels.get(4).add(panels.get(2));

        this.add(panels.get(4), BorderLayout.CENTER);

        panels.get(0).setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(panels.get(0), BorderLayout.NORTH);
    }

    // Method to add an image panel with a scaled image
    private void addImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(252, 255, 195));

        // Load image from resources
        URL imageURL = getClass().getClassLoader().getResource("assets/copyright.png");

        if (imageURL != null) {
            ImageIcon originalIcon = new ImageIcon(imageURL);

            // Scale the image
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Create JLabel with the scaled image
            JLabel imageLabel = new JLabel(scaledIcon);

            // Set layout and add the label to the image panel
            imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            imagePanel.add(imageLabel);

            panels.set(2, imagePanel);
        } else {
            // Print an error message if the image couldn't be loaded
            System.out.println("Failed to load the image: assets/copyright.png");
        }
    }

    // Method to add the title label to the first panel
    private void addTitle() {
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        JLabel titleLabel = new JLabel(model.getTitleAbout());
        titleLabel.setFont(titleFont);
        panels.getFirst().add(titleLabel);
    }

    // Method to add content labels to the third panel
    private void addContent() {
        for (int i = 0; i < AMOUNT_LABELS - 1; i++) {
            JLabel label = new JLabel(model.getTextsList().get(i));
            Font contentFont = new Font("Arial", Font.PLAIN, 16);
            label.setFont(contentFont);
            panels.get(3).add(label);
        }
    }

    // Method to create and initialize panels with background color
    private void createPanels() {
        for (int i = 0; i < AMOUNT_PANELS; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(252, 255, 195));
            panels.add(panel);
        }
    }

    // Method to set up the frame settings
    private void settings(MainController mainController) {
        // Set icon, title, size, location, and other frame settings
        ImageIcon icon = new ImageIcon(mainController.getMainViewModel().getICON_PATH());
        setIconImage(icon.getImage());
        setTitle(model.getTitle());
        setSize(600, 400);
        setLocationRelativeTo(menu);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
