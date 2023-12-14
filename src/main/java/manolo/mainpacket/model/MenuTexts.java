package manolo.mainpacket.model;

import java.util.ArrayList;

public class MenuTexts {
    ArrayList<String> textsButtonList = new ArrayList(); // Textos del Menu
    public MenuTexts() {
        fillTexts();
    }

    private void fillTexts() {
        textsButtonList.add("FTP");      // 0
        textsButtonList.add("EMAIL");    // 1
        textsButtonList.add("CONFIG");   // 2
        textsButtonList.add("LOG OUT");     // 3
    }

    public ArrayList<String> getTextsButtonList() {
        return textsButtonList;
    }

    public void setTextsButtonList(ArrayList<String> textsButtonList) {
        this.textsButtonList = textsButtonList;
    }

}