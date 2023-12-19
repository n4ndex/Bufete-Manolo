package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class NewEmail extends JFrame{
    private JButton enviarButton;
    private JButton cancelarButton;
    private JTextField textField1;
    private JTextArea textArea1;
    private JPanel mainPanel;
    private JButton backButton;

    public NewEmail(){
        initUI();
    }

    private void initUI() {
        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        this.setTitle("New Email");
        this.setSize(width / 3, height / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


}
