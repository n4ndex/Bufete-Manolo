
/**
 * ButtonsListener - ActionListener for handling button events in the FTPWindow of the MainPacket application.
 *
 * This class listens for button click events in the FTPWindow and performs corresponding actions based
 * on the clicked button, such as creating or deleting directories, downloading or uploading files,
 * and refreshing the tree view.
 */

package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.controllermodels.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ButtonsListener implements ActionListener {

    private final MainController mainController;

    public ButtonsListener(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        mainController.getMainConnection().openConnection(mainController.getMainConnectionModel().getMYSQL_URL(), mainController.getMainConnectionModel().getMYSQL_DATABASE(), mainController.getMainConnectionModel().getMYSQL_USERNAME(), mainController.getMainConnectionModel().getPASSWORD());

        if (e.getSource() == mainController.getFtpWindow().getExitButton()) {   // exit to menu button
            mainController.getFtpWindow().dispose();
            mainController.getMenu().setVisible(true);
            mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " leave FTP");
        } else if (e.getSource() == mainController.getFtpWindow().getCreateDirButton()) {   // create dir button
            TreePath selectedPath = mainController.getFtpWindow().getTreeDirectories().getSelectionPath();

            if (selectedPath != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                String selectedDirectory = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);

                String directoryName = Utils.showInputDialog(mainController.getFtpWindow(), mainController.getMainViewModel().getCREATE_DIRECTORY());

                if (directoryName != null && !directoryName.isEmpty()) {
                    String newDirectoryPath = selectedDirectory + File.separator + directoryName;
                    Path newDirectory = Paths.get(newDirectoryPath);
                    if (Utils.isLevelsDeep(newDirectory, 3)) {
                        if (Utils.isValidDNI(directoryName)) {
                            if (mainController.getMainConnection().checkIfUserExists(directoryName)) {
                                int laywerId = mainController.getMainConnection().whichLawyerHasUserAssigned(directoryName);
                                if (laywerId == mainController.getCurrentUser().getId_lawyer()) {
                                    Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getUSER_WITH_DNI_NOT_YOUR_CLIENT(), mainController.getMainViewModel().getWARNING());
                                } else {
                                    createNewDirectory(newDirectoryPath, directoryName);
                                }
                            } else {
                                Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getONLY_PUT_DNI_FROM_EXISTING_USER(), mainController.getMainViewModel().getWARNING());
                            }
                        } else {
                            createNewDirectory(newDirectoryPath, directoryName);
                        }
                    } else {
                        createNewDirectory(newDirectoryPath, directoryName);
                    }
                } else {
                    Utils.showErrorWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getCREATE_DIRECTORY_ERROR() + directoryName, mainController.getMainViewModel().getERROR());
                }
            } else {
                Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getSELECT_FOLDER_BEFORE_CREATE(), mainController.getMainViewModel().getWARNING());
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDeleteDirButton()) {   // delete dir button
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                String selectedDirectoryPath = "" + selectedNode.getUserObject();

                int option = Utils.showConfirmDialog(mainController.getFtpWindow(), mainController.getMainViewModel().getCONFIRM_DELETE_DIRECTORY() + selectedDirectoryPath, mainController.getMainViewModel().getCONFIRM_DELETE_TITLE());

                if (option == JOptionPane.YES_OPTION) {
                    if (mainController.getFtpService().deleteDirectory(selectedDirectoryPath, mainController.getMainClient(), mainController.getFtpWindow())) {
                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " deletes directory: " + selectedDirectoryPath);

                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                    } else {
                        Utils.showErrorWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getDELETE_DIRECTORY_ERROR() + selectedDirectoryPath, mainController.getMainViewModel().getERROR());
                    }
                }
            } else {
                Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getSELECT_DIRECTORY_BEFORE_DELETING(), mainController.getMainViewModel().getWARNING());
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDeleteFileButton()) {  // delete file button
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                String selectedFileName = (String) selectedNode.getUserObject();
                String selectedFilePath = mainController.getFtpWindow().getDirectory() + File.separator + selectedFileName;

                int option = Utils.showConfirmDialog(mainController.getFtpWindow(), mainController.getMainViewModel().getCONFIRM_DELETE_FILE() + selectedFileName, mainController.getMainViewModel().getCONFIRM_DELETE_TITLE());

                if (option == JOptionPane.YES_OPTION) {
                    if (mainController.getFtpService().deleteFile(selectedFilePath, mainController.getMainClient())) {
                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " deletes file: " + selectedFileName);
                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                    } else {
                        Utils.showErrorWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getFILE_DELETE_ERROR() + selectedFileName, mainController.getMainViewModel().getERROR());
                    }
                }
            } else {
                Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getSELECT_FILE_BEFORE_DELETING(), mainController.getMainViewModel().getWARNING());
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDownloadButton()) {    // download file button
            DefaultMutableTreeNode selectedNode = mainController.getFtpWindow().getSelectedNode();
            if (selectedNode != null) {
                String fileName = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);
                String filePath = mainController.getFtpWindow().getDirectory() + "/" + fileName;

                try {
                    if (!mainController.getFtpService().isDirectory(filePath, mainController.getMainClient())) {
                        byte[] fileBytes = mainController.getFtpService().downloadFile(filePath, mainController.getMainClient());

                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle(mainController.getMainViewModel().getFILE_CHOOSER_SAVE_FILE());
                        fileChooser.setSelectedFile(new File(fileName));

                        int userSelection = fileChooser.showSaveDialog(mainController.getFtpWindow());

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            Files.write(selectedFile.toPath(), fileBytes);

                            Utils.showInfoWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getFILE_DOWNLOAD_SUCCESS() + selectedFile.getAbsolutePath(), mainController.getMainViewModel().getINFO());
                            mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " download file: " + selectedFile.getName());
                        }
                    } else {
                        Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getSELECT_FILE_NO_DIRECTORY(), mainController.getMainViewModel().getWARNING());
                    }
                } catch (Exception ex) {
                    Utils.showErrorWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getFILE_DOWNLOAD_ERROR() + ex.getMessage(), mainController.getMainViewModel().getERROR());
                }
            } else {
                Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getNO_FILE_SELECTED(), mainController.getMainViewModel().getWARNING());
            }
        } else if (e.getSource() == mainController.getFtpWindow().getUploadButton()) {  // upload file button
            // Get the selected directory node in the tree
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle(mainController.getMainViewModel().getFILE_CHOOSER_UPLOAD_FILE());

                int userSelection = fileChooser.showOpenDialog(mainController.getFtpWindow());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    String selectedDirectory = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);

                    String remotePath = selectedDirectory + File.separator + selectedFile.getName();

                    try {
                        mainController.getFtpService().uploadFile(selectedFile.getAbsolutePath(), remotePath, mainController.getMainClient());
                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " uploads file: " + selectedFile.getName());

                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                    } catch (Exception ex) {
                        Utils.showErrorWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getFILE_UPLOAD_ERROR() + ex.getMessage(), mainController.getMainViewModel().getERROR());
                    }
                }
            } else {
                Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getSELECT_DIRECTORY_BEFORE_UPLOADING_FILE(), mainController.getMainViewModel().getWARNING());
            }
        } else if (e.getSource() == mainController.getFtpWindow().getRefreshButton()) { // refresh tree button
            mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " refreshes tree");
            mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
        } else if (e.getSource() == mainController.getFtpWindow().getRenameButton()) {  // rename file button
            int option = Utils.showConfirmDialog(mainController.getFtpWindow(), mainController.getMainViewModel().getCONFIRM_RENAME_FILE(), mainController.getMainViewModel().getCONFIRM_RENAME_TITLE());

            if (option == JOptionPane.YES_OPTION) {
                DefaultMutableTreeNode selectedNode = mainController.getFtpWindow().getSelectedNode();
                if (selectedNode != null) {
                    String oldName = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);
                    String newName = mainController.getFtpWindow().getRenameField().getText();
                    String currentPath = mainController.getFtpWindow().getDirectory() + "/" + oldName;
                    String newPath = mainController.getFtpWindow().getDirectory() + "/" + newName;

                    try {
                        if (selectedNode.isLeaf()) {
                            mainController.getFtpService().renameFile(currentPath, newPath, mainController.getMainClient());
                        } else {
                            mainController.getFtpService().renameFile(oldName, newName, mainController.getMainClient());
                        }
                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                        Utils.showInfoWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getFILE_RENAME_SUCCESS(), mainController.getMainViewModel().getINFO());
                    } catch (Exception ex) {
                        Utils.showErrorWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getFILE_RENAME_ERROR() + ex.getMessage(), mainController.getMainViewModel().getERROR());
                    }
                } else {
                    Utils.showWarningWindow(mainController.getFtpWindow(), mainController.getMainViewModel().getNO_FILE_SELECTED(), mainController.getMainViewModel().getWARNING());
                }
            }
        }
        mainController.getMainConnection().closeConnection();
    }

    private void createNewDirectory(String newDirectoryPath, String folderName) {
        mainController.getFtpService().createDirectory(newDirectoryPath, mainController.getMainClient(), mainController.getFtpWindow());
        // Create an empty file inside the new directory
        String emptyFileName = "empty_file.txt";
        String emptyFilePath = newDirectoryPath + File.separator + emptyFileName;
        mainController.getFtpService().createEmptyFile(emptyFilePath, mainController.getMainClient());
        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " creates directory: " + folderName);
        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
    }
}
