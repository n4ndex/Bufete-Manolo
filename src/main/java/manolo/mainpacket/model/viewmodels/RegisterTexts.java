package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class RegisterTexts {
    ArrayList<String> textsList = new ArrayList<>(); // Textos del Register
    ArrayList<String> user_types = new ArrayList<>();   // Tipos de usuarios

    public RegisterTexts() {
        fillTexts();
        fillTypes();
    }

    private void fillTexts() {
        textsList.add("REGISTER");    // 0
        textsList.add("DNI");         // 1
        textsList.add("Nombre");      // 2
        textsList.add("Correo");      // 3
        textsList.add("Contraseña");  // 4
        textsList.add("Seleccione su abogado si desea tener"); // 5
        textsList.add("¿Ya tienes cuenta? Inicia sesión aquí"); // 6
        textsList.add("¿Eres abogado?");    // 7
        textsList.add("Registrarse");     // 0
    }

    private void fillTypes() {
        user_types.add("lawyer");   // 0
        user_types.add("client");   // 1
    }
}
