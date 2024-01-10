package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LoginTexts_es implements LoginTexts {
    private final List<String> textsList = new ArrayList<>(); // Textos del Login

    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";
    private final String ESPANOL_ICON_PATH = "target/classes/assets/espanol.png";
    private final String ENGLISH_ICON_PATH = "target/classes/assets/english.png";

    private String title = "Inicio de sesión en el Bufete";

    public LoginTexts_es() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("LOGIN");       // 0
        textsList.add("DNI");    // 1
        textsList.add("Contraseña");  // 2
        textsList.add("¿No tienes una cuenta? Regístrate aquí"); // 3
        textsList.add("Acceder");     // 4
    }
}
