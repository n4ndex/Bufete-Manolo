package manolo.mainpacket.model.viewmodels;

import java.util.ArrayList;

public class RegisterTexts {
    ArrayList<String> textsList = new ArrayList(); // Textos del Register

    public RegisterTexts() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("REGISTER");    // 0
        textsList.add("DNI");    // 1
        textsList.add("Nombre");      // 2
        textsList.add("Correo electrónico");    // 3
        textsList.add("Contraseña");  // 4
        textsList.add("¿Ya tienes cuenta? Inicia sesión aquí"); // 5
        textsList.add("¿Eres abogado?");    // 6
        textsList.add("Registrarse");     // 0
    }

    public ArrayList<String> getTextsList() {
        return textsList;
    }

    public void setTextsList(ArrayList<String> textsList) {
        this.textsList = textsList;
    }
}
