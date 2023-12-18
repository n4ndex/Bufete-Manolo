package manolo.mainpacket.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class Casos extends JFrame {
    @Getter

    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton crearButton;

    public Casos() {
        initUI();
    }

    private void initUI() {
        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (pantalla.getHeight() + 200);
        int width = pantalla.width;

        this.setTitle("Casos del Bufete");
        this.setSize(width / 2, height / 2);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
