package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

@Getter
public class MainViewModel_es implements MainViewModel {
    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";
    private final String WELCOME = "¡Bienvenido ";
    private final String ERROR = "Error";
    private final String WARNING = "Advertencia";
    private final String INFO = "Info";
    private final String LOGIN_ERROR = "Error al iniciar sesión. DNI o contraseña incorrecta.";
    private final String USER_EXISTS = "El usuario ya existe, por favor ingrese otro o inicie sesión";
    private final String INVALID_DNI = "Por favor, ingrese un DNI válido (8 dígitos seguidos de una letra)";
    private final String INVALID_EMAIL = "Por favor, ingrese un correo electrónico válido";
    private final String INVALID_PASSWORD = "La contraseña debe contener al menos 8 caracteres, una letra, un número y un carácter especial.";
    private final String FILE_RENAME_ERROR = "Error al renombrar el archivo: ";
    private final String FILE_DOWNLOAD_ERROR = "Error al descargar el archivo: ";
    private final String FILE_DELETE_ERROR = "Error al eliminar el archivo: ";
    private final String FILE_RENAME_SUCCESS = "Archivo renombrado exitosamente.";
    private final String NO_FILE_SELECTED = "Ningún archivo seleccionado.";
    private final String FTP_NOT_STARTED = "Error: Servidor FTP no iniciado.";
    private final String DIRECTORY_CREATED_SUCCESS = "Directorio creado exitosamente: ";
    private final String CREATE_DIRECTORY_ERROR = "Error al crear: ";
    private final String DELETE_DIRECTORY_ERROR = "Error al eliminar: ";
    private final String DIRECTORY_DELETED_SUCCESS = "Directorio eliminado exitosamente: ";
    private final String SELECT_FILE_NO_DIRECTORY = "No se puede descargar un directorio. Por favor, seleccione un archivo.";
    private final String SELECT_FOLDER_BEFORE_CREATE = "Seleccione un directorio antes de crear un nuevo directorio.";
    private final String USER_WITH_DNI_NOT_YOUR_CLIENT = "El usuario con ese DNI no es tu cliente.";
    private final String ONLY_PUT_DNI_FROM_EXISTING_USER = "Sólo puedes poner DNI si es de un usuario existente.";
    private final String SELECT_DIRECTORY_BEFORE_DELETING = "Seleccione un directorio antes de eliminar.";
    private final String SELECT_FILE_BEFORE_DELETING = "Seleccione un archivo antes de eliminar.";
    private final String SELECT_DIRECTORY_BEFORE_UPLOADING_FILE = "Selecciona un directorio antes de subir el archivo.";
    private final String NO_FILE_CHOSEN_TO_RENAME = "Ningún archivo seleccionado para renombrar.";
    private final String USER_CREATED_SUCCESS = "Usuario creado correctamente";
    private final String FILE_DOWNLOAD_SUCCESS = "Archivo descargado exitosamente en la ruta:";
    private final String FILE_UPLOAD_ERROR = "Error al subir el archivo: ";
    private final String CASE_EMPTY = "Por favor, introduzca un nombre de caso.";
    private final String CONFIRM_DELETE_DIRECTORY = "¿Seguro que quieres eliminar el directorio?\n";
    private final String CONFIRM_DELETE_FILE = "¿Seguro que quieres eliminar el archivo?\n";
    private final String CONFIRM_DELETE_TITLE = "Confirmar eliminación";
    private final String CONFIRM_RENAME_FILE = "¿Seguro que quieres renombrar?";
    private final String CONFIRM_RENAME_TITLE = "Confirmar renombre";
    private final String CREATE_DIRECTORY = "Ingrese el nombre de la carpeta";
    private final String FILE_CHOOSER_SAVE_FILE = "Guardar archivo";
    private final String FILE_CHOOSER_UPLOAD_FILE = "Subir archivo";
}
