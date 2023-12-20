package manolo.mainpacket.controller.databaseconnection;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

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
    public boolean loginLawyer(String dni, String password) {

        String query = """
                SELECT * FROM users 
                WHERE dni = ? AND password = ? AND user_type_id = ?""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, 1);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean loginClient(String dni, String password) {

        String query = """
                SELECT * FROM users 
                WHERE dni = ? AND password = ? AND user_type_id = ?""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, 2);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkIfUserExists(String username) {
        boolean exists = false;
        try {
            String query = """
                    SELECT *
                    FROM users
                    WHERE dni = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exists;
    }

    public int insertNewUser(User currentUser) {
        int rowsAffected = 0;
        String query = """
                INSERT INTO users (dni, name, password, email, user_type_id)
                VALUES (?, ?, ?, ?, ?)
                ;
                """;
        int user_type_id = getUserTypeIdFromTypeName(currentUser.getUserType().getType());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentUser.getDni());
            preparedStatement.setString(2, currentUser.getName());
            preparedStatement.setString(3, currentUser.getPassword());
            preparedStatement.setString(4, currentUser.getEmail());
            preparedStatement.setInt(5, user_type_id);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsAffected;
    }

    private int getUserTypeIdFromTypeName(String type) {
        int user_type_id = 0;
        try {
            String query = """
                    SELECT id_type
                    FROM user_types
                    WHERE type_name = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user_type_id = resultSet.getInt("id_type");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user_type_id;
    }
}
