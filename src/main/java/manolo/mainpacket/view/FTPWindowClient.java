package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.FTPTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
@Setter
public class FTPWindowClient extends JFrame {
    private JPanel mainPanel;
    private JList list1;
    private JButton downloadButton;
    private JLabel serverLabel;
    private JLabel clientLabel;
    private MainController mainController;
    private FTPTexts model = new FTPTexts();

    public FTPWindowClient(MainController mainController) {
        this.mainController = mainController;
        initComponents();
        initUI();
    }

    private void initComponents() {
        serverLabel.setText(model.getTextsList().get(11));
        clientLabel.setText(model.getTextsList().get(12));
        downloadButton.setText(model.getTextsList().get(13));
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
