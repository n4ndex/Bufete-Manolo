package manolo.mainpacket.model.viewmodels;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EmailTexts_en implements EmailTexts {

    private String title = "New Email";

    ArrayList<Integer> numDimensions = new ArrayList<>();
    ArrayList<String> texts = new ArrayList<>();

    public EmailTexts_en() {
        numbers();
        strings();
    }

    private void numbers() {
        numDimensions.add(800); //0- width x view
        numDimensions.add(720); //1- height y view
        numDimensions.add(350); //2- location x view
        numDimensions.add(50);  //3- location y view
    }

    public void strings() {
        texts.add("New Email");        //0- new email button name
        texts.add("BACK TO MENU");              //1- back button name
        texts.add("Inbox");             //2- side panel option 1
        texts.add("Deleted");           //3- side panel option 2
        texts.add("Sent");              //4- side panel option 3
        texts.add("Notes");             //5- side panel option 4
        texts.add("Send");              //6
        texts.add("Cancel");            //7
        texts.add("Attach Files");      //8
        texts.add("To: ");              //9
        texts.add("Subject: ");         //10
        texts.add("Write your message");//11
        texts.add("...");               //12
    }
}
