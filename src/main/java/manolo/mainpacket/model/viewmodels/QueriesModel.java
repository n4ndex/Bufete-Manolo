package manolo.mainpacket.model.viewmodels;

public class QueriesModel {
    public static final String GET_USER_DATA = """
            SELECT users.*, user_types.*
            FROM users
            INNER JOIN user_types ON users.user_type_id = user_types.id_type
            WHERE users.dni = ? AND users.password = ?
            """;

    public static final String GET_LAWYER_NAMES = """
            SELECT name
            FROM users
            INNER JOIN user_types ON users.user_type_id = user_types.id_type
            WHERE users.user_type_id = 1
            """;

    public static final String GET_CLIENT_NAMES = """
            SELECT name
            FROM users
            INNER JOIN user_types ON users.user_type_id = user_types.id_type
            WHERE users.user_type_id = 2
            """;

    public static final String GET_ID_FROM_NAME = "SELECT id_user FROM users WHERE name = ?";

    public static final String GET_DNI_FROM_NAME = "SELECT dni FROM users WHERE name = ?";

    public static final String CHECK_IF_USER_EXISTS = """
            SELECT *
            FROM users
            WHERE dni = ?
            """;

    public static final String INSERT_NEW_USER = """
            INSERT INTO users (dni, name, password, email, user_type_id, id_lawyer)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static final String GET_USER_TYPE_ID_FROM_TYPE_NAME = """
            SELECT id_type
            FROM user_types
            WHERE type_name = ?
            """;

    public static final String UPDATE_CLIENT_LAWYER_ID = "UPDATE users SET id_lawyer = ? WHERE name = ? AND user_type_id = 2";

    public static final String INSERT_LOG = "INSERT INTO logs (operation, date, time, id_user) VALUES (?, CURDATE(), CURTIME(), (SELECT id_user FROM users WHERE dni = ?))";

    public static final String WHICH_LAWYER_HAS_USER_ASSIGNED = """
            SELECT id_lawyer
            FROM users
            WHERE dni = ?
            """;
}
