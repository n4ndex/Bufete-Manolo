package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

@Getter
public class MainViewModel_en implements MainViewModel {
    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";
    private final String WELCOME = "Welcome ";
    private final String ERROR = "Error";
    private final String WARNING = "Warning";
    private final String INFO = "Info";
    private final String LOGIN_ERROR = "Error logging in. Incorrect DNI or password.";
    private final String USER_EXISTS = "User already exists. Please enter another one or log in.";
    private final String INVALID_DNI = "Please enter a valid DNI (8 digits followed by a letter).";
    private final String INVALID_EMAIL = "Please enter a valid email.";
    private final String INVALID_PASSWORD = "Password must contain at least 8 characters, one letter, one number, and one special character.";
    private final String FILE_RENAME_ERROR = "Error renaming: ";
    private final String FILE_DOWNLOAD_ERROR = "Error downloading file: ";
    private final String FILE_DELETE_ERROR = "Error deleting file: ";
    private final String FILE_RENAME_SUCCESS = "Successfully renamed.";
    private final String NO_FILE_SELECTED = "No file selected.";
    private final String FTP_NOT_STARTED = "Error: FTP server not started.";
    private final String DIRECTORY_CREATED_SUCCESS = "Directory created successfully: ";
    private final String CREATE_DIRECTORY_ERROR = "Error creating directory: ";
    private final String DELETE_DIRECTORY_ERROR = "Error deleting directory: ";
    private final String DIRECTORY_DELETED_SUCCESS = "Directory deleted successfully: ";
    private final String SELECT_FILE_NO_DIRECTORY = "Cannot download a directory. Please select a file.";
    private final String SELECT_FOLDER_BEFORE_CREATE = "Select a directory before creating a new one.";
    private final String USER_WITH_DNI_NOT_YOUR_CLIENT = "The user with that DNI is not your client.";
    private final String ONLY_PUT_DNI_FROM_EXISTING_USER = "You can only input an DNI if it belongs to an existing user.";
    private final String SELECT_DIRECTORY_BEFORE_DELETING = "Select a directory before deleting.";
    private final String SELECT_FILE_BEFORE_DELETING = "Select a file before deleting.";
    private final String SELECT_DIRECTORY_BEFORE_UPLOADING_FILE = "Select a directory before uploading a file.";
    private final String NO_FILE_CHOSEN_TO_RENAME = "Nothing selected for renaming.";
    private final String USER_CREATED_SUCCESS = "User created successfully.";
    private final String FILE_DOWNLOAD_SUCCESS = "File downloaded successfully at the path: ";
    private final String FILE_UPLOAD_ERROR = "Error uploading file: ";
    private final String CASE_EMPTY = "Please enter a case name.";
    private final String CONFIRM_DELETE_DIRECTORY = "Are you sure you want to delete the directory?\n";
    private final String CONFIRM_DELETE_FILE = "Are you sure you want to delete the file?\n";
    private final String CONFIRM_DELETE_TITLE = "Confirm Deletion";
    private final String CONFIRM_RENAME_FILE = "Are you sure you want to rename?";
    private final String CONFIRM_RENAME_TITLE = "Confirm Rename";
    private final String CREATE_DIRECTORY = "Enter the folder name";
    private final String FILE_CHOOSER_SAVE_FILE = "Save file";
    private final String FILE_CHOOSER_UPLOAD_FILE = "Upload file";
}
