package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.viewmodels.MenuTexts;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

@Getter
@Setter
public class Menu extends JFrame {

    // Lists to store buttons and labels
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();

    // Model for menu texts
    private MenuTexts model = new MenuTexts();

    // Constructor
    public Menu() {
        createButtons(); // Create menu buttons
        addButtonsToFrame(); // Add buttons to the frame
        settings(); // Set frame settings
    }

    // Method to create menu buttons
    private void createButtons() {
        int desiredWidth = 120;
        int desiredHeight = 120;

        // Loop through each button text in the model
        for (String buttonText : model.getTextsButtonList()) {
            String imagePath = "assets/" + buttonText + ".png";
            URL imageURL = getClass().getClassLoader().getResource(imagePath);

            // Check if the image can be loaded
            if (imageURL != null) {
                // Resize and set up the button with the image
                ImageIcon originalIcon = new ImageIcon(imageURL);
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                JButton button = new JButton(resizedIcon);
                button.setName(buttonText);

                button.setLayout(new BorderLayout());

                button.setFocusPainted(false);

                JLabel label = new JLabel(buttonText, SwingConstants.CENTER);
                button.add(label, BorderLayout.SOUTH);

                button.setBackground(Color.WHITE);

                Font customFont = new Font("Arial", Font.BOLD, 20);
                label.setFont(customFont);

                buttons.add(button);
            } else {
                // Print an error message if the image couldn't be loaded
                System.out.println("Failed to load the image: " + imagePath);
            }
        }
    }

    // Method to add buttons to the frame
    private void addButtonsToFrame() {
        setLayout(new GridLayout(1, model.getTextsButtonList().size()));

        for (JButton button : buttons) {
            add(button);
        }
    }

    // Method to set frame settings
    private void settings() {
        ImageIcon icon = new ImageIcon(model.getICON_PATH());
        setIconImage(icon.getImage());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screen.height;
        int screenWidth = screen.width;

        this.setSize(screenWidth / 2, screenHeight / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
