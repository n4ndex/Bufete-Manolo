package manolo.mainpacket.controller.databaseconnection;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Setter
public class MainConnection {
    private Connection connection;

    public MainConnection(Driver driver) {
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void openConnection(String url, String database, String user, String password) {
        try {
            connection = DriverManager.getConnection(url + database, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
