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
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();
    private MenuTexts model = new MenuTexts();

    public Menu() {
        createButtons();
        addButtonsToFrame();

        settings();
    }

    private void addButtonsToFrame() {
        setLayout(new GridLayout(1, model.getTextsButtonList().size())); // Una columna, tantas filas como botones

        for (JButton button : buttons) {
            add(button);
        }
    }

    private void createButtons() {
        int desiredWidth = 120;
        int desiredHeight = 120;

        for (String buttonText : model.getTextsButtonList()) {
            String imagePath = "assets/" + buttonText + ".png";
            URL imageURL = getClass().getClassLoader().getResource(imagePath);

            if (imageURL != null) {
                ImageIcon originalIcon = new ImageIcon(imageURL);
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                JButton button = new JButton(resizedIcon);
                button.setName(buttonText);

                button.setLayout(new BorderLayout());

                JLabel label = new JLabel(buttonText, SwingConstants.CENTER);
                button.add(label, BorderLayout.SOUTH);

                button.setBackground(Color.WHITE);

                Font customFont = new Font("Arial", Font.BOLD, 24);
                label.setFont(customFont);

                buttons.add(button);
            } else {
                System.out.println("No se pudo cargar la imagen: " + imagePath);
            }
        }
    }

    private void settings() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        this.setTitle("Menu Bufete");
        this.setSize(width/2, height/3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
