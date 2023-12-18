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
}
