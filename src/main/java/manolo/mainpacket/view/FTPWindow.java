package manolo.mainpacket.view;

import lombok.Getter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

@Getter

public class FTPWindow extends JFrame {
    private JTextField textField1;
    private JButton createDirButton;
    private JButton deleteFileButton;
    private JButton uploadButton;
    private JButton downloadButton;
    private JButton exitButton;
    private JButton deleteDirButton;
    private JTree treeDirectories;
    private JPanel mainPanel;

    public FTPWindow() {
        initComponents();
        initUI();
    }

    private void initComponents() {
        File rootDirectory = new File("C:\\Users\\Usuario\\Pictures");
        loadDirectory(rootDirectory);
    }


    private void loadDirectory(File directory) {
        DefaultMutableTreeNode rootNode = createNodes(directory);
        treeDirectories.setModel(new DefaultTreeModel(rootNode));
    }

    private DefaultMutableTreeNode createNodes(File file) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    rootNode.add(createNodes(childFile));
                }
            }
        }
        return rootNode;
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
