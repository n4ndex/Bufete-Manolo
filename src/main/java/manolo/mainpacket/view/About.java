package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.viewmodels.AboutTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

@Getter
@Setter
public class About extends JFrame {
    private final int amountPanels = 5;
    private final int amountLabels = 8;
    private final ArrayList<JPanel> panels = new ArrayList<>();
    private final AboutTexts model = new AboutTexts();

    public About(JFrame menu) {
        settings(menu);

        createPanels(amountPanels);
        addTitle();
        addContent();
        addImagePanel();
        combinePanels();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                menu.setVisible(true);
            }
        });

        setVisible(true);
    }

    private void settings(JFrame menu) {
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());
        setTitle(model.getTitle());
        setSize(600, 400);
        setLocationRelativeTo(menu);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void combinePanels() {
        panels.get(3).setLayout(new GridLayout(0, 1, 10, 20));
        panels.get(4).setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        panels.get(4).add(panels.get(3));
        panels.get(4).add(panels.get(2));

        this.add(panels.get(4), BorderLayout.CENTER);

        panels.get(0).setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(panels.get(0), BorderLayout.NORTH);
    }

    private void addImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(252, 255, 195));

        URL imageURL = getClass().getClassLoader().getResource("assets/copyright.png");

        if (imageURL != null) {
            ImageIcon originalIcon = new ImageIcon(imageURL);

            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(scaledIcon);

            imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            imagePanel.add(imageLabel);

            panels.set(2, imagePanel);
        } else {
            System.out.println("No se pudo cargar la imagen: assets/copyright.png");
        }
    }

    private void addTitle() {
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        JLabel titleLabel = new JLabel(model.getTitleAbout());
        titleLabel.setFont(titleFont);
        panels.get(0).add(titleLabel);
    }

    private void addContent() {
        for (int i = 0; i < amountLabels - 1; i++) {
            JLabel label = new JLabel(model.getTextsList().get(i));
            Font contentFont = new Font("Arial", Font.PLAIN, 16);
            label.setFont(contentFont);
            panels.get(3).add(label);
        }
    }

    private void createPanels(int max) {
        for (int i = 0; i < max; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(252, 255, 195));
            panels.add(panel);
        }
    }
}
