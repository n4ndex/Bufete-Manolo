package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AboutTexts_es implements AboutTexts {
    private ArrayList<String> textsList = new ArrayList<>(); // Textos del About

    private String title = "Acerca De Bufete de Abogados";

    private String titleAbout = "Bufete de Abogados Manager v1.0";

    public AboutTexts_es() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("Desarrolladores:");              // 0
        textsList.add("- Diego Fernández Rojo");        // 1
        textsList.add("- David Maestre Díaz");          // 2
        textsList.add("- Hugo Villodres Moreno");       // 3
        textsList.add("- Isaac Requena Santiago");      // 4
        textsList.add("© 2024 Bufete Manolo S.A.");     // 5
        textsList.add("Todos los derechos reservados.");// 6
        textsList.add("Versión de la Aplicación: v1.0");// 7
    }
}
