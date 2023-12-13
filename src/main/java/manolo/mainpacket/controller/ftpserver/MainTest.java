package manolo.mainpacket.controller.ftpserver;

import org.apache.commons.net.ftp.FTPClient;

public class MainTest {
    private String homeDirectory = "\\";
    private String currertDirectory = "\\";

    public static void main(String[] args) throws Exception {
        FtpService ftpService = new FtpService();
        FTPClient client = ftpService.loginFtp("127.0.0.1", 21, "root", "");
        ftpService.printTree("", client);
    }

}
