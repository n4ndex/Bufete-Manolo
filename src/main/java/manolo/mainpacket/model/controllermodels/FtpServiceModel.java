package manolo.mainpacket.model.controllermodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FtpServiceModel {
    private String host = "127.0.0.1";
    private int port = 21;
    private String usernameLawyer = "lawyer";
    private String usernameClient = "client";
    private String password = "";
}
