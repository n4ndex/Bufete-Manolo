package manolo.mainpacket.model.controllermodels;

import lombok.Getter;
import lombok.Setter;

import java.sql.Driver;
import java.sql.SQLException;

@Getter
@Setter
public class MainConnectionModel {
    private final Driver DRIVER;
    private final String MYSQL_URL = "jdbc:mysql://localhost/";
    private final String MYSQL_DATABASE = "manolodatabase";
    private final String MYSQL_USERNAME = "root";
    private final String PASSWORD = "";

    public MainConnectionModel() {
        try {
            DRIVER = new com.mysql.cj.jdbc.Driver();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
