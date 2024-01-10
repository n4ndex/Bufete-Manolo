package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.controller.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

                String folderName = JOptionPane.showInputDialog(mainController.getFtpWindow(), "Ingrese el nombre de la carpeta:");

                if (folderName != null && !folderName.isEmpty()) {
                    String newDirectoryPath = selectedDirectory + File.separator + folderName;
                    Path newDirectory = Paths.get(newDirectoryPath);
                    if (Utils.isLevelsDeep(newDirectory, 3)) {
                        if (mainController.isValidDNI(folderName)) {
                            if (mainController.getMainConnection().checkIfUserExists(folderName)) {
                                int laywerId = mainController.getMainConnection().whichLawyerHasUserAssigned(folderName);
                                if (laywerId == mainController.getCurrentUser().getId_lawyer()) {
                                    mainController.showWarningWindow(mainController.getFtpWindow(), "El usuario con ese DNI no es tu cliente.");
                                } else {
                                    createNewDirectory(newDirectoryPath, folderName);
                                }
                            } else {
                                mainController.showWarningWindow(mainController.getFtpWindow(), "Sólo puedes poner DNI si es de un usuario existente.");
                            }
                        }else{
                            createNewDirectory(newDirectoryPath, folderName);
                        }
                    } else {
                        createNewDirectory(newDirectoryPath, folderName);
                    }
                } else {
                    mainController.showErrorWindow(mainController.getFtpWindow(), "Error al crear la carpeta.");
                }
            } else {
                mainController.showWarningWindow(mainController.getFtpWindow(), "Seleccione una carpeta origen antes de crear una nueva.");
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDeleteDirButton()) {   // delete dir button
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                String selectedDirectoryPath = "" + selectedNode.getUserObject();

                int option = JOptionPane.showConfirmDialog(mainController.getFtpWindow(), "¿Seguro que quieres eliminar el directorio?\n" + selectedDirectoryPath, "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    if (mainController.getFtpService().deleteDirectory(selectedDirectoryPath, mainController.getMainClient(), mainController.getFtpWindow())) {
                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " deletes directory: " + selectedDirectoryPath);

                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                    } else {
                        mainController.showErrorWindow(mainController.getFtpWindow(), "Error al eliminar el directorio.");
                    }
                }
            } else {
                mainController.showWarningWindow(mainController.getFtpWindow(), "Selecciona un directorio para eliminar.");
            }
        } else if (e.getSource() == mainController.getFtpWindow().getDeleteFileButton()) {  // delete file button
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                String selectedFileName = (String) selectedNode.getUserObject();
                String selectedFilePath = mainController.getFtpWindow().getDirectory() + File.separator + selectedFileName;

                int option = JOptionPane.showConfirmDialog(mainController.getFtpWindow(), "¿Seguro que deseas eliminar el archivo '" + selectedFileName + "'?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    if (mainController.getFtpService().deleteFile(selectedFilePath, mainController.getMainClient())) {
                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " deletes file: " + selectedFileName);
                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                    } else {
                        mainController.showErrorWindow(mainController.getFtpWindow(), "Error al eliminar el archivo.");
                    }
                }
            } else {
                mainController.showWarningWindow(mainController.getFtpWindow(), "Selecciona un archivo para eliminar.");
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
                        fileChooser.setDialogTitle("Guardar archivo");
                        fileChooser.setSelectedFile(new File(fileName));
                        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos", ".*"));

                        int userSelection = fileChooser.showSaveDialog(mainController.getFtpWindow());

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            Files.write(selectedFile.toPath(), fileBytes);

                            mainController.showInfoWindow(mainController.getFtpWindow(), "Archivo descargado exitosamente en la ruta: " + selectedFile.getAbsolutePath());
                            mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " download file: " + selectedFile.getName());
                        }
                    } else {
                        mainController.showErrorWindow(mainController.getFtpWindow(), "No se puede descargar un directorio. Por favor, seleccione un archivo.");
                    }
                } catch (Exception ex) {
                    mainController.showErrorWindow(mainController.getFtpWindow(), "Error al descargar el archivo: " + ex.getMessage());
                }
            } else {
                mainController.showWarningWindow(mainController.getFtpWindow(), "Ningún archivo seleccionado para descargar.");
            }
        } else if (e.getSource() == mainController.getFtpWindow().getUploadButton()) {  // upload file button
            // Get the selected directory node in the tree
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) mainController.getFtpWindow().getTreeDirectories().getLastSelectedPathComponent();

            if (selectedNode != null) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Selecciona un archivo a subir");

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
                        mainController.showErrorWindow(mainController.getFtpWindow(), "Error subiendo archivo: " + ex.getMessage());
                    }
                }
            } else {
                mainController.showWarningWindow(mainController.getFtpWindow(), "Selecciona un directorio antes de subir el archivo.");
            }
        } else if (e.getSource() == mainController.getFtpWindow().getRefreshButton()) { // refresh tree button
            mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " refreshes tree");
            mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
        } else if (e.getSource() == mainController.getFtpWindow().getRenameButton()) {  // rename file button
            int option = JOptionPane.showConfirmDialog(mainController.getFtpWindow(), "¿Seguro deseas renombrar?", "Renombrar", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                DefaultMutableTreeNode selectedNode = mainController.getFtpWindow().getSelectedNode();
                if (selectedNode != null) {
                    String oldName = mainController.getFtpWindow().getSelectedDirectoryPath(selectedNode);
                    String newName = mainController.getFtpWindow().getRenameField().getText();
                    String currentPath = mainController.getFtpWindow().getDirectory() + "/" + oldName;
                    String newPath = mainController.getFtpWindow().getDirectory() + "/" + newName;

                    try {
                        mainController.getFtpService().renameFile(currentPath, newPath, mainController.getMainClient());
                        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " renames file: " + oldName + " to: " + newName);

                        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
                        mainController.showInfoWindow(mainController.getFtpWindow(), "Archivo renombrado exitosamente.");
                    } catch (Exception ex) {
                        mainController.showErrorWindow(mainController.getFtpWindow(), "Error al renombrar el archivo: " + ex.getMessage());
                    }
                } else {
                    mainController.showWarningWindow(mainController.getFtpWindow(), "Ningún archivo seleccionado para renombrar.");
                }
            }
        }
        mainController.getMainConnection().closeConnection();
    }

    private void createNewDirectory(String newDirectoryPath, String folderName) {
        mainController.getFtpService().createDirectory(newDirectoryPath, mainController.getMainClient(), mainController.getFtpWindow());
        // Crear un archivo vacío dentro de la carpeta recién creada
        String emptyFileName = "empty_file.txt";
        String emptyFilePath = newDirectoryPath + File.separator + emptyFileName;
        mainController.getFtpService().createEmptyFile(emptyFilePath, mainController.getMainClient());
        mainController.getMainConnection().insertLog(mainController.getCurrentUser().getDni(), mainController.getCurrentUser().getName() + " creates directory: " + folderName);
        mainController.getFtpWindow().loadDirectory(mainController.getMainClient(), mainController.getFtpWindow().getLawyerDni());
    }
}
