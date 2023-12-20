package manolo.mainpacket.controller.ftpserver;

import manolo.mainpacket.controller.MainController;
import manolo.mainpacket.view.FTPWindow;
import org.apache.commons.net.*;
import org.apache.commons.net.ftp.*;

import javax.swing.*;
import java.io.*;

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
                System.out.printf("[%s][%d] Command sent : [%s]-%s", Thread.currentThread().getName(),
                        System.currentTimeMillis(), protocolCommandEvent.getCommand(),
                        protocolCommandEvent.getMessage());
            }

            @Override
            public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
                System.out.printf("[%s][%d] Reply received : %s", Thread.currentThread().getName(),
                        System.currentTimeMillis(), protocolCommandEvent.getMessage());
            }
        });


        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
        } catch (IOException e) {
            mainController.showErrorWindow(mainController.getLoginView(), "Error: Servidor FTP no iniciado.");
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
                mainController.showInfoWindow(ftpWindow, "Directorio creado exitosamente: " + newDirectoryPath);
            } else {
                mainController.showErrorWindow(ftpWindow, "Error al crear: " + newDirectoryPath);
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
                mainController.showInfoWindow(ftpWindow, "Directorio eliminado exitosamente: " + path);
                return true;
            } else {
                mainController.showErrorWindow(ftpWindow, "Error al eliminar: " + path);
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
            System.out.printf("[uploadFile][%d] Is success to upload file : %s -> %b",
                    System.currentTimeMillis(), remotePath, ftpClient.storeFile(remotePath, fileInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public void renameFile(String oldPath, String newPath, FTPClient ftpClient) {
        System.out.println();
        try {
            System.out.printf("[renameFile][%d] Is success to rename file : from %s to %s -> %b",
                    System.currentTimeMillis(), oldPath, newPath, ftpClient.rename(oldPath, newPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public byte[] downloadFile(String path, FTPClient ftpClient) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.out.println();
        try {
            System.out.printf("[downloadFile][%d] Is success to download file : %s -> %b",
                    System.currentTimeMillis(), path, ftpClient.retrieveFile(path, byteArrayOutputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        return byteArrayOutputStream.toByteArray();
    }

    public boolean deleteFile(String path, FTPClient ftpClient) {
        try {
            boolean success = ftpClient.deleteFile(path);
            System.out.printf("[deleteFile][%d] Is success to delete file : %s -> %b%n",
                    System.currentTimeMillis(), path, success);
            return success;
        } catch (IOException e) {
            System.out.printf("[deleteFile][%d] Error deleting file : %s%n",
                    System.currentTimeMillis(), path);
            e.printStackTrace();
            return false;
        }
    }

}