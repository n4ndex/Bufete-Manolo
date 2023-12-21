package manolo.mainpacket.view;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.viewmodels.CasosTexts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FTPWindowClient extends JFrame {
    private JPanel mainPanel;
    private JList list1;
    private JButton descargarButton;
    private MainController mainController;
    private CasosTexts model = new CasosTexts();

    public FTPWindowClient(MainController mainController) {
        this.mainController = mainController;
        initUI();
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
