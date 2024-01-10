package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class MenuTexts {
    ArrayList<String> textsButtonList = new ArrayList(); // Textos del Menu

    private final String ICON_PATH = "target/classes/assets/icon_app.jpg";

    public MenuTexts() {
        fillTexts();
    }

    private void fillTexts() {
        textsButtonList.add("FTP");      // 0
        textsButtonList.add("EMAIL");    // 1
        textsButtonList.add("CASOS");    // 2
        textsButtonList.add("ACERCA");   // 3
        textsButtonList.add("LOG OUT");  // 4
    }
}
