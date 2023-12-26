package manolo.mainpacket.controller.databaseconnection;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.User;
import manolo.mainpacket.model.UserType;

import java.sql.*;
import java.util.ArrayList;

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

    public User getUserData(String dni, String password) {
        String query = """
            SELECT users.*, user_types.*
            FROM users
            INNER JOIN user_types ON users.user_type_id = user_types.id_type
            WHERE users.dni = ? AND users.password = ?""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String userType = resultSet.getString("type_name");
                    int idlawyer = resultSet.getInt("id_lawyer");
                    int privilegeLevel = resultSet.getInt("privilege_level");

                    UserType userTypeEnum = new UserType(userType, privilegeLevel);
                    return new User(dni, name, password, email, userTypeEnum, idlawyer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ArrayList<String> getLawyerNames() {
        ArrayList<String> lawyerNames = new ArrayList<>();

        String query = """
        SELECT name
        FROM users
        INNER JOIN user_types ON users.user_type_id = user_types.id_type
        WHERE users.user_type_id = 1""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                lawyerNames.add(name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lawyerNames;
    }

    public int getLawyerIdFromName(String lawyerName) {
        if (lawyerName.isEmpty() || lawyerName.equals("")) {
            return 0; // Si el nombre del abogado está vacío o es "", asigna id_lawyer = 0
        }

        String query = "SELECT id_user FROM users WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, lawyerName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_user");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
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
                INSERT INTO users (dni, name, password, email, user_type_id, id_lawyer)
                VALUES (?, ?, ?, ?, ?, ?)
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
            preparedStatement.setInt(6, currentUser.getId_lawyer());
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

    public void insertLog(String userDni, String operation) {
        try {
            String query = "INSERT INTO logs (operation, date, time, id_user) VALUES (?, CURDATE(), CURTIME(), (SELECT id_user FROM users WHERE dni = ?))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, operation);
            preparedStatement.setString(2, userDni);
            preparedStatement.executeUpdate();
            System.out.println("Log insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en la inserción del log.");
        }
    }
}
