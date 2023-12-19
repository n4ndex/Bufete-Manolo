package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.ftpserver.FtpService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public class FTPWindow extends JFrame {
    private JTextField renameField;
    private JButton createDirButton;
    private JButton deleteFileButton;
    private JButton uploadButton;
    private JButton downloadButton;
    private JButton exitButton;
    private JButton deleteDirButton;
    private JButton refreshButton;
    private JTree treeDirectories;
    private JPanel mainPanel;
    private JLabel rutaLabel;
    private JLabel DNILabel;
    private JLabel serverLabel;
    private String directory = "";
    private FTPClient ftpClient;
    private FtpService ftpService;

    public FTPWindow(FTPClient ftpClient, FtpService ftpService) {
        this.ftpClient = ftpClient;
        this.ftpService = ftpService;
        initComponents();
        initUI();
    }

    private void initComponents() {
        loadDirectory(ftpClient);
    }

    private void initUI() {

        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (pantalla.getHeight() + 300);
        int width = (int) (pantalla.width + 200);

        this.setTitle("Gestión de Ficheros del Bufete");
        this.setSize(width / 2, height / 3);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public DefaultMutableTreeNode getSelectedNode() {
        TreePath selectedPath = treeDirectories.getSelectionPath();
        if (selectedPath != null) {
            return (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        }
        return null;
    }

    public String getSelectedDirectoryPath(DefaultMutableTreeNode selectedNode) {
        while (selectedNode != null) {
            if (selectedNode.getUserObject() != null) {
                return selectedNode.getUserObject().toString();
            }
            selectedNode = (DefaultMutableTreeNode) selectedNode.getParent();
        }

        return "";
    }


    private DefaultMutableTreeNode createNodes(String path) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(path);
        FTPFile[] ftpFiles = ftpService.listFiles(path, ftpClient);

        if (ftpFiles != null) {
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.isDirectory()) {
                    rootNode.add(createNodes(path + "/" + ftpFile.getName()));
                } else {
                    rootNode.add(new DefaultMutableTreeNode(ftpFile.getName()));
                }
            }
        }

        return rootNode;
    }

    public void loadDirectory(FTPClient ftpClient) {
        try {
            directory = ftpClient.printWorkingDirectory();

            DefaultMutableTreeNode rootNode = createNodes(directory);

            treeDirectories.setModel(new DefaultTreeModel(rootNode));

            rutaLabel.setText("Ruta actual: " + directory);
        } catch (Exception e) {
            System.err.println("Error: Servidor FTP no iniciado.");
        }
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
