package manolo.mainpacket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String dni;
    private String name;
    private String password;
    private String email;
    private UserType userType;
    private int id_lawyer;

    public User(String dni, String name, String password, String email, UserType userType, int id_lawyer) {
        this.dni = dni;
        this.name = name;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.id_lawyer = id_lawyer;
    }
}
