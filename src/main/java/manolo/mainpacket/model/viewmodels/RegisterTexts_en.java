package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class RegisterTexts_en implements RegisterTexts {
    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";

    ArrayList<String> textsList = new ArrayList<>(); // Register Texts
    ArrayList<String> userTypes = new ArrayList<>();  // User Types

    String title = "Register at Law Firm";

    public RegisterTexts_en() {
        fillTexts();
        fillTypes();
    }

    private void fillTexts() {
        textsList.add("REGISTER");    // 0
        textsList.add("DNI");           // 1
        textsList.add("Name");         // 2
        textsList.add("Email");        // 3
        textsList.add("Password");     // 4
        textsList.add("Select your lawyer if desired"); // 5
        textsList.add("Already have an account? Log in here"); // 6
        textsList.add("Are you a lawyer?");    // 7
        textsList.add("Register");     // 0
    }

    private void fillTypes() {
        userTypes.add("lawyer");   // 0
        userTypes.add("client");   // 1
    }
}
