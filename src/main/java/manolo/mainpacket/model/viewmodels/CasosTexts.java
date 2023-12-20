package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CasosTexts {
    ArrayList<String> textsList = new ArrayList(); // Textos de Casos

    String title = "Casos del Bufete";
    public CasosTexts() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("CREAR CASOS");              // 0
        textsList.add("Selecciona Cliente");    // 1
        textsList.add("Introduce el nombre del caso");         // 2
        textsList.add("Crear");     // 3
    }
}
