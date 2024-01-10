package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class NewEmail extends JFrame{
    private JButton sendButton;
    private JButton cancelButton;
    private JButton addFilesButton;
    private JLabel filesNameLabel;
    private JTextField toTextfield;
    private JTextArea subjetTextArea;
    private JPanel mainPanel;
    private JTextField messageTextfield;

    public NewEmail(){
        initUI();
    }

    private void initUI() {
        ImageIcon icon = new ImageIcon("target/classes/assets/icon_app.jpg");
        setIconImage(icon.getImage());
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
