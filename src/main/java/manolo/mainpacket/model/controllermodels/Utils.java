package manolo.mainpacket.model.controllermodels;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class Utils {
    public static boolean isLevelsDeep(Path path, int levelsToCheck) {
        int levels = path.getNameCount();
        return levels == levelsToCheck;
    }

    public static void showErrorWindow(Component parentComponent, String errorMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningWindow(Component parentComponent, String warningMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, warningMessage, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfoWindow(Component parentComponent, String infoMessage, String title) {
        JOptionPane.showMessageDialog(parentComponent, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmDialog(Component parentComponent, String message, String title) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, JOptionPane.YES_NO_OPTION);
    }

    public static String showInputDialog(Component parentComponent, String message) {
        return JOptionPane.showInputDialog(parentComponent, message);
    }

    public static boolean isValidDNI(String dni) {
        String dniRegex = "^[0-9]{8}[A-Za-z]$";
        return dni.matches(dniRegex);
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }
}
