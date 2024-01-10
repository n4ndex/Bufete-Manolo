package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AboutTexts_en implements AboutTexts {
    private ArrayList<String> textsList = new ArrayList<>(); // About Texts

    private String title = "About Law Firm";

    private String titleAbout = "Law Firm Manager v1.0";

    public AboutTexts_en() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("Developers:");                  // 0
        textsList.add("- Diego Fernández Rojo");        // 1
        textsList.add("- David Maestre Díaz");          // 2
        textsList.add("- Hugo Villodres Moreno");       // 3
        textsList.add("- Isaac Requena Santiago");      // 4
        textsList.add("© 2024 Manolo Law Firm Inc.");   // 5
        textsList.add("All rights reserved.");          // 6
        textsList.add("Application Version: v1.0");     // 7
    }
}
