package manolo.mainpacket.model.viewmodels;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EmailTexts_es implements EmailTexts {

    private String title = "Nuevo Email";

    ArrayList<Integer> numDimensions = new ArrayList<>();
    ArrayList<String> texts = new ArrayList<>();

    public EmailTexts_es() {
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
        texts.add("Nuevo correo");      //0- new email button name
        texts.add("VUELTA A MENÚ");     //1- back button name
        texts.add("Bandeja de entrada");//2- side panel option 1
        texts.add("Spam");          //3- side panel option 2
        texts.add("Enviados");          //4- side panel option 3
        texts.add("Borradores");             //5- side panel option 4
        texts.add("Enviar");            //6
        texts.add("Cancelar");          //7
        texts.add("Adjuntar archivos"); //8
        texts.add("Para: ");            //9
        texts.add("Asunto: ");          //10
        texts.add("Escribe el mensaje");//11
        texts.add("...");               //12
    }
}
