package manolo.mainpacket.view;

import lombok.Getter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Getter

public class FTPWindow extends JFrame {
    private JTextField renameField;
    private JButton createDirButton;
    private JButton deleteFileButton;
    private JButton uploadButton;
    private JButton downloadButton;
    private JButton exitButton;
    private JButton deleteDirButton;
    private JTree treeDirectories;
    private JPanel mainPanel;
    private JLabel rutaLabel;
    private JLabel DNILabel;
    private String directory = "C:\\Users\\Usuario\\Pictures";

    public FTPWindow() {
        initComponents();
        initUI();
    }

    private void initComponents() {
        rutaLabel.setText("Ruta actual: " + directory);
        File rootDirectory = new File(directory);
        loadDirectory(rootDirectory);
    }

    private void initUI() {

        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (pantalla.getHeight() + 200);
        int width = pantalla.width;

        this.setTitle("Gestión de Ficheros del Bufete");
        this.setSize(width / 2, height / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void loadDirectory(File directory) {
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

    public String getFullPath(DefaultMutableTreeNode node) {
        StringBuilder fullPath = new StringBuilder();
        TreeNode[] path = node.getPath();

        for (int i = 1; i < path.length; i++) {
            fullPath.append(path[i].toString());
            if (i < path.length - 1) {
                fullPath.append(File.separator);
            }
        }
        return fullPath.toString();
    }


    public boolean deleteDirectory(File directoryToDelete) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar la carpeta y su contenido?",
                "Confirmar eliminación de " + directoryToDelete,
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            if (directoryToDelete.isDirectory()) {
                File[] files = directoryToDelete.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteDirectory(file);
                        } else {
                            file.delete();
                        }
                    }
                }
            }
            return directoryToDelete.delete();
        }

        return false;
    }

    public boolean deleteFile(File fileToDelete) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas eliminar el archivo?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            return fileToDelete.delete();
        }

        return false;
    }

        public void save(File selectedFile) {
        File downloadsFolder = new File(System.getProperty("user.home"), "Downloads");
        Path destinationPath = downloadsFolder.toPath().resolve(selectedFile.getName());

        try {
            Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Descarga completada en: " + destinationPath.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo en Descargas.");
        }
    }

    public void saveAs(File selectedFile) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Archivo");
        fileChooser.setSelectedFile(new File(selectedFile.getName()));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File destinationFile = fileChooser.getSelectedFile();

            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(this, "Descarga completada en: " + destinationFile.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
            }
        }
    }


    public void uploadFile(File selectedFile) {

    }
}
