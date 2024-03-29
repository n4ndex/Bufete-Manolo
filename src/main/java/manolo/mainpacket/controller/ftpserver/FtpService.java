/**
 * FtpService - Handles FTP server interactions for the MainPacket application.
 * <p>
 * This class provides methods for connecting to an FTP server, listing files and directories,
 * creating directories, uploading and downloading files, renaming and deleting files and directories,
 * and retrieving client-specific files from the FTP server.
 */

package manolo.mainpacket.controller.ftpserver;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.model.controllermodels.Utils;
import manolo.mainpacket.view.FTPWindow;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * FtpService - Handles FTP server interactions for the MainPacket application.
 * This class provides methods for connecting to an FTP server, listing files and directories, creating directories, uploading and downloading files, renaming and deleting files and directories, and retrieving client-specific files from the FTP server.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
public class FtpService {
    private final MainController mainController;

    public FtpService(MainController mainController) {
        this.mainController = mainController;
    }

    public FTPClient loginFtp(String host, int port, String username, String password) {
        FTPClient ftpClient = new FTPClient();
        ftpClient.addProtocolCommandListener(new ProtocolCommandListener() {
            @Override
            public void protocolCommandSent(ProtocolCommandEvent protocolCommandEvent) {
                System.out.printf("[%s][%d] Command sent : [%s]-%s", Thread.currentThread().getName(), System.currentTimeMillis(), protocolCommandEvent.getCommand(), protocolCommandEvent.getMessage());
            }

            @Override
            public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
                System.out.printf("[%s][%d] Reply received : %s", Thread.currentThread().getName(), System.currentTimeMillis(), protocolCommandEvent.getMessage());
            }
        });
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
        } catch (IOException e) {
            Utils.showErrorWindow(mainController.getLoginView(), mainController.getMainViewModel().getFTP_NOT_STARTED(), mainController.getMainViewModel().getERROR());
        }
        return ftpClient;
    }

    public FTPFile[] listFiles(String path, FTPClient ftpClient) {
        try {
            return ftpClient.listFiles(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isDirectory(String path, FTPClient ftpClient) {
        try {
            FTPFile[] files = ftpClient.listFiles(path);
            return files != null && files.length > 0 && files[0].isDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDirectory(String newDirectoryPath, FTPClient ftpClient, FTPWindow ftpWindow) {
        try {
            if (ftpClient.makeDirectory(newDirectoryPath)) {
                Utils.showInfoWindow(ftpWindow, mainController.getMainViewModel().getDIRECTORY_CREATED_SUCCESS() + newDirectoryPath, mainController.getMainViewModel().getINFO());
            } else {
                Utils.showErrorWindow(ftpWindow, mainController.getMainViewModel().getCREATE_DIRECTORY_ERROR() + newDirectoryPath, mainController.getMainViewModel().getERROR());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEmptyFile(String filePath, FTPClient ftpClient) {
        try {
            InputStream inputStream = new ByteArrayInputStream(new byte[0]);
            boolean success = ftpClient.storeFile(filePath, inputStream);
            if (success) {
                System.out.printf("[createEmptyFile][%d] Empty file created successfully: %s%n", System.currentTimeMillis(), filePath);
            } else {
                System.out.printf("[createEmptyFile][%d] Error creating empty file: %s%n", System.currentTimeMillis(), filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteDirectory(String path, FTPClient ftpClient, FTPWindow ftpWindow) {
        try {
            FTPFile[] files = ftpClient.listFiles(path);
            if (files != null) {
                for (FTPFile file : files) {
                    String filePath = path + "/" + file.getName();

                    if (file.isDirectory()) {
                        deleteDirectory(filePath, ftpClient, ftpWindow);
                    } else {
                        ftpClient.deleteFile(filePath);
                    }
                }
            }
            if (ftpClient.removeDirectory(path)) {
                Utils.showInfoWindow(ftpWindow, mainController.getMainViewModel().getDIRECTORY_DELETED_SUCCESS() + path, mainController.getMainViewModel().getINFO());
                return true;
            } else {
                Utils.showErrorWindow(ftpWindow, mainController.getMainViewModel().getDELETE_DIRECTORY_ERROR() + path, mainController.getMainViewModel().getERROR());
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFile(String localPath, String remotePath, FTPClient ftpClient) {
        try {
            FileInputStream fileInputStream = new FileInputStream(localPath);
            System.out.println();
            System.out.printf("[uploadFile][%d] Is success to upload file : %s -> %b", System.currentTimeMillis(), remotePath, ftpClient.storeFile(remotePath, fileInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public void renameFile(String oldPath, String newPath, FTPClient ftpClient) {
        System.out.println();
        try {
            System.out.printf("[renameFile][%d] Is success to rename file : from %s to %s -> %b", System.currentTimeMillis(), oldPath, newPath, ftpClient.rename(oldPath, newPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public byte[] downloadFile(String path, FTPClient ftpClient) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.out.println();
        try {
            System.out.printf("[downloadFile][%d] Is success to download file : %s -> %b", System.currentTimeMillis(), path, ftpClient.retrieveFile(path, byteArrayOutputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        return byteArrayOutputStream.toByteArray();
    }

    public boolean deleteFile(String path, FTPClient ftpClient) {
        try {
            boolean success = ftpClient.deleteFile(path);
            System.out.printf("[deleteFile][%d] Is success to delete file : %s -> %b%n", System.currentTimeMillis(), path, success);
            return success;
        } catch (IOException e) {
            System.out.printf("[deleteFile][%d] Error deleting file : %s%n", System.currentTimeMillis(), path);
            e.printStackTrace();
            return false;
        }
    }

    public String[] getClientFiles(String clientDni, FTPClient ftpClient) {
        List<String> fileNames = new ArrayList<>();
        searchClientFiles("/", clientDni, ftpClient, fileNames);
        return fileNames.toArray(new String[0]);
    }

    private void searchClientFiles(String currentPath, String clientDni, FTPClient ftpClient, List<String> fileNames) {
        FTPFile[] files = listFiles(currentPath, ftpClient);
        for (FTPFile file : files) {
            String filePath = currentPath + file.getName();
            Path filePathPath = Paths.get(filePath);
            if (file.isDirectory()) {
                searchClientFiles(filePath + "/", clientDni, ftpClient, fileNames);
            } else if (filePathPath.getNameCount() >= 4) {
                if (filePathPath.getName(2).toString().equals(clientDni)) {
                    fileNames.add(filePath);
                }
            }
        }
    }
}
