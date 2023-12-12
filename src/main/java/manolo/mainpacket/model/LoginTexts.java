package manolo.mainpacket.model;

import java.util.ArrayList;

public class LoginTexts {
    ArrayList<String>textsList = new ArrayList(); // Textos del Login
    public LoginTexts() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("LOGIN");       // 0
        textsList.add("NickName");    // 1
        textsList.add("Nombre");      // 2
        textsList.add("Contraseña");  // 3
        textsList.add("Acceder");     // 4
    }

    public ArrayList<String> getTextsList() {
        return textsList;
    }

    public void setTextsList(ArrayList<String> textsList) {
        this.textsList = textsList;
    }
}
