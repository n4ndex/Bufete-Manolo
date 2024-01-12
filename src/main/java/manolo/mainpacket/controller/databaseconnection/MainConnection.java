package manolo.mainpacket.controller.databaseconnection;

import lombok.Getter;
import lombok.Setter;
import manolo.mainpacket.model.User;
import manolo.mainpacket.model.UserType;
import manolo.mainpacket.model.viewmodels.QueriesModel;

import java.sql.*;
import java.util.ArrayList;

/**
 * MainConnection - Handles the database connection and executes queries for the MainPacket application.
 * This class provides methods to interact with the database, such as retrieving user data, obtaining lists of lawyer and client names, checking user existence, inserting new users, and updating client lawyer assignments, among other database operations.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
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
        String query = QueriesModel.GET_USER_DATA;
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
        String query = QueriesModel.GET_LAWYER_NAMES;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                lawyerNames.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lawyerNames;
    }

    public ArrayList<String> getClientNames() {
        ArrayList<String> clientNames = new ArrayList<>();
        String query = QueriesModel.GET_CLIENT_NAMES;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                clientNames.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientNames;
    }

    public int getIdFromName(String name) {
        if (name.isEmpty() || name.equals("")) {
            return 0; // Si el nombre del abogado está vacío o es "", asigna id_lawyer = 0
        }
        String query = QueriesModel.GET_ID_FROM_NAME;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
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

    public String getDniFromName(String name) {
        if (name.isEmpty() || name.equals("")) {
            return "";
        }
        String query = QueriesModel.GET_DNI_FROM_NAME;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("dni");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public boolean checkIfUserExists(String username) {
        boolean exists = false;
        try {
            String query = QueriesModel.CHECK_IF_USER_EXISTS;
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
        String query = QueriesModel.INSERT_NEW_USER;
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
            String query = QueriesModel.GET_USER_TYPE_ID_FROM_TYPE_NAME;
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

    public void updateClientLawyerId(String clientName, int newLawyerId) {
        String query = QueriesModel.UPDATE_CLIENT_LAWYER_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newLawyerId);
            preparedStatement.setString(2, clientName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertLog(String userDni, String operation) {
        try {
            String query = QueriesModel.INSERT_LOG;
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

    public int whichLawyerHasUserAssigned(String userDni) {
        int idLawyer = 0;
        try {
            String query = QueriesModel.WHICH_LAWYER_HAS_USER_ASSIGNED;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userDni);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idLawyer = resultSet.getInt("id_lawyer");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idLawyer;
    }
}
