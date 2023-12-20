package manolo.mainpacket.view;

import lombok.Getter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.CasosTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
public class Casos extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitle;
    private JComboBox comboClient;
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
    }

    private void setButtonTexts() {
        buttonCrear.setText(model.getTextsList().get(3));
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
