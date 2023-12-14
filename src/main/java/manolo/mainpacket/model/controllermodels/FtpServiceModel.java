package manolo.mainpacket.model.controllermodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FtpServiceModel {
    private String host;
    private int port;
    private String username;
    private String password;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
