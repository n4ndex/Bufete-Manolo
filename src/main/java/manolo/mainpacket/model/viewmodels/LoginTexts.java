package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class LoginTexts {
    ArrayList<String>textsList = new ArrayList(); // Textos del Login

    String title = "Login Bufete";
    public LoginTexts() {
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
