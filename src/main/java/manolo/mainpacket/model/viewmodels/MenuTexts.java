package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class MenuTexts {
    ArrayList<String> textsButtonList = new ArrayList(); // Textos del Menu

    public MenuTexts() {
        fillTexts();
    }

    private void fillTexts() {
        textsButtonList.add("FTP");      // 0
        textsButtonList.add("EMAIL");    // 1
        textsButtonList.add("CASOS");    // 2
        textsButtonList.add("CONFIG");   // 3
        textsButtonList.add("LOG OUT");  // 4
    }
}
