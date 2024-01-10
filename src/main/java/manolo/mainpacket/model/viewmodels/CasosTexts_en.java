package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CasosTexts_en implements CasosTexts {
    ArrayList<String> textsList = new ArrayList(); // Case Texts

    String title = "Law Firm Cases";

    public CasosTexts_en() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("CREATE CASES");                  // 0
        textsList.add("Select Client");                // 1
        textsList.add("Enter the case name");           // 2
        textsList.add("Create");                        // 3
    }
}
