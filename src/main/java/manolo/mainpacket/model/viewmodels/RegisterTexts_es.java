package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class RegisterTexts_es implements RegisterTexts {
    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";

    ArrayList<String> textsList = new ArrayList<>(); // Textos del Register
    ArrayList<String> userTypes = new ArrayList<>();   // Tipos de usuarios

    String title = "Registro en el Bufete";

    public RegisterTexts_es() {
        fillTexts();
        fillTypes();
    }

    private void fillTexts() {
        textsList.add("REGISTRO");    // 0
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
        userTypes.add("lawyer");   // 0
        userTypes.add("client");   // 1
    }

}
