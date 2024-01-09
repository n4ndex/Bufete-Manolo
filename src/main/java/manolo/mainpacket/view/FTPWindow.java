package manolo.mainpacket.view;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.ftpserver.FtpService;
import manolo.mainpacket.model.viewmodels.FTPTexts;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;

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
    private JLabel nameLabel;
    private JLabel serverLabel;
    private JLabel renameLabel;
    private JButton renameButton;
    private String directory = "";
    private String lawyerDni = "";
    private String lawyerName = "";
    private FTPClient ftpClient;
    private FtpService ftpService;
    private FTPTexts model = new FTPTexts();

    public FTPWindow(MainController mainController) {
        this.ftpClient = mainController.getMainClient();
        this.ftpService = mainController.getFtpService();
        initComponents();
        initUI();
    }

    private void initComponents() {
        loadDirectory(ftpClient, lawyerDni);
        setLabelTexts();
        setButtonTexts();
    }

    private void setLabelTexts() {
        nameLabel.setText(model.getTextsList().get(0));
        rutaLabel.setText(model.getTextsList().get(1));
        serverLabel.setText(model.getTextsList().get(2));
        renameLabel.setText(model.getTextsList().get(10));
    }

    private void setButtonTexts() {
        createDirButton.setText(model.getTextsList().get(3));
        deleteDirButton.setText(model.getTextsList().get(4));
        uploadButton.setText(model.getTextsList().get(5));
        deleteFileButton.setText(model.getTextsList().get(6));
        downloadButton.setText(model.getTextsList().get(7));
        exitButton.setText(model.getTextsList().get(8));
        refreshButton.setText(model.getTextsList().get(9));
        renameButton.setText(model.getTextsList().get(11));
    }

    private void initUI() {
        this.setContentPane(mainPanel);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (pantalla.getHeight() + 300);
        int width = (int) (pantalla.width + 200);

        this.setTitle(model.getTitle());
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

    public void loadDirectory(FTPClient ftpClient, String path) {
        try {
            directory = ftpClient.printWorkingDirectory().substring(1);
            String fullPath = directory + "/" + path;
            loadDirectoryInternal(ftpClient, fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadDirectoryInternal(FTPClient ftpClient, String path) {
        try {
            DefaultMutableTreeNode rootNode = createNodes(path);

            treeDirectories.setModel(new DefaultTreeModel(rootNode));

            rutaLabel.setText(model.getTextsList().get(1) + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
