package manolo.mainpacket.controller.ftpserver;

import manolo.mainpacket.controller.databaseconnection.MainConnection;
import org.apache.commons.net.*;
import org.apache.commons.net.ftp.*;

import java.io.*;

public class FtpService {

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
            throw new RuntimeException(e);
        }
        return ftpClient;
    }

    public void printFtpClientInfo(FTPClient ftpClient) {
        System.out.println();
        try {
            System.out.printf("[printFtpClientInfo][%d] Get system type : %s %n", System.currentTimeMillis(), ftpClient.getSystemType());
            System.out.printf("[printFtpClientInfo][%d] Get reply code : %d %n", System.currentTimeMillis(), ftpClient.getReplyCode());
            System.out.printf("[printFtpClientInfo][%d] Get reply string : %s %n", System.currentTimeMillis(), ftpClient.getReplyString());
            System.out.printf("[printFtpClientInfo][%d] Get remote address : %s %n", System.currentTimeMillis(), ftpClient.getRemoteAddress());
            System.out.printf("[printFtpClientInfo][%d] Get remote port : %d %n", System.currentTimeMillis(), ftpClient.getRemotePort());
            System.out.printf("[printFtpClientInfo][%d] Get local address : %s %n", System.currentTimeMillis(), ftpClient.getLocalAddress());
            System.out.printf("[printFtpClientInfo][%d] Get local port : %d %n", System.currentTimeMillis(), ftpClient.getLocalPort());
            System.out.printf("[printFtpClientInfo][%d] Get control encoding : %s %n", System.currentTimeMillis(), ftpClient.getControlEncoding());
            System.out.printf("[printFtpClientInfo][%d] Get data timeout : %s %n", System.currentTimeMillis(), ftpClient.getDataTimeout().toString());
            System.out.printf("[printFtpClientInfo][%d] Get buffer size : %d %n", System.currentTimeMillis(), ftpClient.getBufferSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printTree(String path, FTPClient ftpClient) {
        try {
            for (FTPFile ftpFile : ftpClient.listFiles(path)) {
                System.out.println();
                System.out.printf("[printTree][%d]%n", System.currentTimeMillis());
                System.out.printf("[printTree][%d] Get name : %s %n", System.currentTimeMillis(), ftpFile.getName());
                System.out.printf("[printTree][%d] Get timestamp : %s %n", System.currentTimeMillis(), ftpFile.getTimestamp().getTimeInMillis());
                System.out.printf("[printTree][%d] Get group : %s %n", System.currentTimeMillis(), ftpFile.getGroup());
                System.out.printf("[printTree][%d] Get link : %s %n", System.currentTimeMillis(), ftpFile.getLink());
                System.out.printf("[printTree][%d] Get user : %s %n", System.currentTimeMillis(), ftpFile.getUser());
                System.out.printf("[printTree][%d] Get type : %s %n", System.currentTimeMillis(), ftpFile.getType());
                System.out.printf("[printTree][%d] Is file : %s %n", System.currentTimeMillis(), ftpFile.isFile());
                System.out.printf("[printTree][%d] Is directory : %s %n", System.currentTimeMillis(), ftpFile.isDirectory());
                System.out.printf("[printTree][%d] Formatted string : %s %n", System.currentTimeMillis(), ftpFile.toFormattedString());
                System.out.println();

                if (ftpFile.isDirectory()) {
                    printTree(path + File.separator + ftpFile.getName(), ftpClient);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getTreeFromDirectory(String path, FTPClient ftpClient) {
        File file = new File(path);
        try {
            for (FTPFile ftpFile : ftpClient.listFiles(path)) {
                if (ftpFile.isDirectory()) {
                    getTreeFromDirectory(path + File.separator + ftpFile.getName(), ftpClient);
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream(path + File.separator + ftpFile.getName());
                    ftpClient.retrieveFile(path + File.separator + ftpFile.getName(), fileOutputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public void createDirectory(String path, FTPClient ftpClient) {
        System.out.println();
        try {
            System.out.printf("[createDirectory][%d] Is success to create directory : %s -> %b",
                    System.currentTimeMillis(), path, ftpClient.makeDirectory(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
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

    public void deleteFile(String path, FTPClient ftpClient) {
        System.out.println();
        try {
            System.out.printf("[deleteFile][%d] Is success to delete file : %s -> %b",
                    System.currentTimeMillis(), path, ftpClient.deleteFile(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }

    public void deleteDirectory(String path, FTPClient ftpClient) {
        System.out.println();
        try {
            System.out.printf("[deleteDirectory][%d] Is success to delete directory : %s -> %b",
                    System.currentTimeMillis(), path, ftpClient.removeDirectory(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
    }
}