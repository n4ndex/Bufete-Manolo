package manolo.mainpacket.model.viewmodels;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EmailTexts {

    ArrayList<Integer> numDimensions = new ArrayList<>();
    ArrayList<String> texts = new ArrayList<>();

    public EmailTexts() {
        numbers();
        strings();
    }

    private void numbers() {
        numDimensions.add(800); //0- width x view
        numDimensions.add(720); //1- height y view
        numDimensions.add(350); //2- size x view
        numDimensions.add(50);  //3- size y view
    }

    public void strings() {
        texts.add("Nuevo correo");      //0- new email button name
        texts.add("BACK");              //1- back button name
        texts.add("Bandeja de entrada");//2- side panel option 1
        texts.add("Enviados");          //3- side panel option 2
        texts.add("Borrados");          //4- side panel option 3
        texts.add("Notas");             //5- side panel option 4
    }
}
