package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class FTPTexts_en implements FTPTexts {

    ArrayList<String> textsList = new ArrayList(); // FTP Texts

    String title = "Law Firm File Management";
    public FTPTexts_en() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("Name: ");             // 0
        textsList.add("User Type: ");        // 1
        textsList.add("Server: ");           // 2
        textsList.add("Create Folder");      // 3
        textsList.add("Delete Folder");      // 4
        textsList.add("Upload File");        // 5
        textsList.add("Delete File");        // 6
        textsList.add("Download File");      // 7
        textsList.add("Back to Menu");       // 8
        textsList.add("↻");                  // 9
        textsList.add("Rename: ");           // 10
        textsList.add("Apply");              // 11
    }

}
