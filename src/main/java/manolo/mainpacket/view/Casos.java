package manolo.mainpacket.view;

import lombok.Getter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.CasosTexts;

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
    private MainController mainController;
    private CasosTexts model = new CasosTexts();

    public Casos(MainController mainController) {
        this.mainController = mainController;
        initUI();
        initComponents();
    }

    private void initComponents() {
        setLabelTexts();
        setButtonTexts();
        createClientCombo();
    }

    private void createClientCombo() {
        ArrayList<String> clientNames = mainController.getMainConnection().getClientNames();

        for (String clientName : clientNames) {
            comboClient.addItem(clientName);
        }
    }

    private void setButtonTexts() {
        buttonCrear.setText(model.getTextsList().get(3));

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        buttonCrear.setFont(buttonFont);
        buttonCrear.setBackground(new Color(255, 215, 0));  // Dorado
    }

    private void setLabelTexts() {
        labelTitle.setText(model.getTextsList().get(0));
        labelClient.setText(model.getTextsList().get(1));
        labelCase.setText(model.getTextsList().get(2));

    }

    private void initUI() {
        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());
        this.setTitle(model.getTitle());
        this.setSize(width / 3, height / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                mainController.getMenu().setVisible(true); // Hace visible el menú al cerrar "Casos"
            }
        });
        this.setVisible(true);
    }
}
