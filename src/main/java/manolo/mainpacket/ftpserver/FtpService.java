package manolo.mainpacket.ftpserver;

import org.apache.commons.net.*;
import org.apache.commons.net.ftp.*;

import java.io.*;

public class FtpService {

    public FTPClient loginFtp(String host, int port, String username, String password) throws Exception {
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
        ftpClient.connect(host, port);
        ftpClient.login(username, password);
        return ftpClient;
    }

    public void printFtpClientInfo(FTPClient ftpClient) throws IOException {
        System.out.println();
        System.out.printf("[printFtpClientInfo][%d] Get system name : %s \n", System.currentTimeMillis(), ftpClient.getSystemName());
        System.out.printf("[printFtpClientInfo][%d] Get system type : %s \n", System.currentTimeMillis(), ftpClient.getSystemType());
        System.out.printf("[printFtpClientInfo][%d] Get default port : %d \n", System.currentTimeMillis(), ftpClient.getDefaultPort());
        System.out.printf("[printFtpClientInfo][%d] Get remote address : %s \n", System.currentTimeMillis(), ftpClient.getRemoteAddress());
        System.out.printf("[printFtpClientInfo][%d] Get remote port : %d \n", System.currentTimeMillis(), ftpClient.getRemotePort());
        System.out.printf("[printFtpClientInfo][%d] Get local address : %s \n", System.currentTimeMillis(), ftpClient.getLocalAddress());
        System.out.printf("[printFtpClientInfo][%d] Get local port : %d \n", System.currentTimeMillis(), ftpClient.getLocalPort());
        System.out.printf("[printFtpClientInfo][%d] Get control encoding : %s \n", System.currentTimeMillis(), ftpClient.getControlEncoding());
        System.out.printf("[printFtpClientInfo][%d] Get connect timeout : %d \n", System.currentTimeMillis(), ftpClient.getConnectTimeout());
        System.out.printf("[printFtpClientInfo][%d] Get default timeout : %d \n", System.currentTimeMillis(), ftpClient.getDefaultTimeout());
        System.out.printf("[printFtpClientInfo][%d] Get data timeout : %s \n", System.currentTimeMillis(), ftpClient.getDataTimeout().toString());
        System.out.printf("[printFtpClientInfo][%d] Get remote verification enabled : %b \n", System.currentTimeMillis(), ftpClient.isRemoteVerificationEnabled());
    }

    public void printTree(String path, FTPClient ftpClient) throws Exception {
        for (FTPFile ftpFile : ftpClient.listFiles(path)) {
            System.out.println();
            System.out.printf("[printTree][%d]\n", System.currentTimeMillis());
            System.out.printf("[printTree][%d] Get name : %s \n", System.currentTimeMillis(), ftpFile.getName());
            System.out.printf("[printTree][%d] Get timestamp : %s \n", System.currentTimeMillis(), ftpFile.getTimestamp().getTimeInMillis());
            System.out.printf("[printTree][%d] Get group : %s \n", System.currentTimeMillis(), ftpFile.getGroup());
            System.out.printf("[printTree][%d] Get link : %s \n", System.currentTimeMillis(), ftpFile.getLink());
            System.out.printf("[printTree][%d] Get user : %s \n", System.currentTimeMillis(), ftpFile.getUser());
            System.out.printf("[printTree][%d] Get type : %s \n", System.currentTimeMillis(), ftpFile.getType());
            System.out.printf("[printTree][%d] Is file : %s \n", System.currentTimeMillis(), ftpFile.isFile());
            System.out.printf("[printTree][%d] Is directory : %s \n", System.currentTimeMillis(), ftpFile.isDirectory());
            System.out.printf("[printTree][%d] Formatted string : %s \n", System.currentTimeMillis(), ftpFile.toFormattedString());
            System.out.println();

            if (ftpFile.isDirectory()) {
                printTree(path + File.separator + ftpFile.getName(), ftpClient);
            }
        }
    }

    public void createDirectory(String path, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[createDirectory][%d] Is success to create directory : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.makeDirectory(path));
        System.out.println();
    }

    public void uploadFile(String localPath, String remotePath, FTPClient ftpClient) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(localPath);
        System.out.println();
        System.out.printf("[uploadFile][%d] Is success to upload file : %s -> %b",
                System.currentTimeMillis(), remotePath, ftpClient.storeFile(remotePath, fileInputStream));
        System.out.println();
    }

    public void renameFile(String oldPath, String newPath, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[renameFile][%d] Is success to rename file : from %s to %s -> %b",
                System.currentTimeMillis(), oldPath, newPath, ftpClient.rename(oldPath, newPath));
        System.out.println();
    }

    public byte[] downloadFile(String path, FTPClient ftpClient) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.out.println();
        System.out.printf("[downloadFile][%d] Is success to download file : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.retrieveFile(path, byteArrayOutputStream));
        System.out.println();
        return byteArrayOutputStream.toByteArray();
    }

    public void deleteFile(String path, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[deleteFile][%d] Is success to delete file : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.deleteFile(path));
        System.out.println();
    }

    public void deleteDirectory(String path, FTPClient ftpClient) throws Exception {
        System.out.println();
        System.out.printf("[deleteDirectory][%d] Is success to delete directory : %s -> %b",
                System.currentTimeMillis(), path, ftpClient.removeDirectory(path));
        System.out.println();
    }
}