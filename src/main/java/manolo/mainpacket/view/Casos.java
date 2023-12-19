package manolo.mainpacket.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
@Getter
public class Casos extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitle;
    private JComboBox comboClient;
    private JTextField textFieldCaso;
    private JButton crearButton;
    private JLabel labelClient;
    private JLabel labelCase;

    public Casos() {
        initUI();
    }

    private void initUI() {
        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        this.setTitle("Casos del Bufete");
        this.setSize(width / 3, height / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
