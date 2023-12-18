package manolo.mainpacket.controller.ftpserver;

import org.apache.commons.net.ftp.FTPClient;

public class MainTest {

    public static void main(String[] args) throws Exception {
        FtpService ftpService = new FtpService();
        FTPClient ftpClient = ftpService.loginFtp("127.0.0.1", 21, "root", "");
        ftpService.crearDirectory("/si", ftpClient);
    }

}
