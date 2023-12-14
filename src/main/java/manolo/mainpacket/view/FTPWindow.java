package manolo.mainpacket.view;

import javax.swing.*;
import java.awt.*;

public class FTPWindow extends JFrame {
    private JTextField textField1;
    private JButton createDirButton;
    private JButton deleteFileButton;
    private JButton uploadButton;
    private JButton downloadButton;
    private JButton exitButton;
    private JButton deleteDirButton;
    private JTree tree1;
    private JPanel mainPanel;

    public FTPWindow() {
        initComponents();
        initUI();
    }

    private void initComponents() {
        textField1 = new JTextField(20);
        createDirButton = new JButton("Crear carpeta");
        deleteFileButton = new JButton("Eliminar archivo");
        uploadButton = new JButton("Subir archivo");
        downloadButton = new JButton("Descargar archivo");
        exitButton = new JButton("Salir");
        deleteDirButton = new JButton("Eliminar Carpeta");
        tree1 = new JTree();
    }

    private void initUI() {

        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;

        this.setTitle("Menu Bufete");
        this.setSize(width/2, height/3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
    }

}
