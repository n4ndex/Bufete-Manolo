package manolo.mainpacket.model.viewmodels;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class FTPTexts_es implements FTPTexts {

    ArrayList<String> textsList = new ArrayList(); // Textos del FTP

    String title = "Gestión de Ficheros del Bufete";
    public FTPTexts_es() {
        fillTexts();
    }

    private void fillTexts() {
        textsList.add("Nombre: ");             // 0
        textsList.add("Ruta: ");            // 1
        textsList.add("Servidor: ");        // 2
        textsList.add("Crear carpeta");     // 3
        textsList.add("Eliminar Carpeta");  // 4
        textsList.add("Subir archivo");     // 5
        textsList.add("Eliminar archivo");  // 6
        textsList.add("Descargar archivo"); // 7
        textsList.add("Volver al menú");    // 8
        textsList.add("↻");                 // 9
        textsList.add("Renombrar: ");       // 10
        textsList.add("Aplicar");           // 11
    }

}
