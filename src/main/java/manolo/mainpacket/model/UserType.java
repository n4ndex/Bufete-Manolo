package manolo.mainpacket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserType {
    private String type;
    private int privilegeLevel;

    public UserType(String type, int privilegeLevel) {
        this.type = type;
        this.privilegeLevel = privilegeLevel;
    }
}
