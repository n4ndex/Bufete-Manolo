package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LoginTexts_en implements LoginTexts {
    private final List<String> textsList = new ArrayList<>(); // Login Texts

    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";
    private final String ESPANOL_ICON_PATH = "target/classes/assets/espanol.png";
    private final String ENGLISH_ICON_PATH = "target/classes/assets/english.png";

    private String title = "Law Firm Login";

    public LoginTexts_en() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("LOGIN");         // 0
        textsList.add("DNI");            // 1
        textsList.add("Password");      // 2
        textsList.add("Don't have an account? Register here"); // 3
        textsList.add("Login");         // 4
    }
}
