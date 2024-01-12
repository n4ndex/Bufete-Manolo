package manolo.mainpacket;

import manolo.mainpacket.controller.MainController;

/**
 * MainExecutable - Entry point for the MainPacket application.
 * This class initializes the MainController, which serves as the main controller for the application.
 *
 * @author Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 * @version 1.0
 */
public class MainExecutable {
    /**
     * Main method for the MainPacket application.
     *
     * @param args Arguments for the application.
     */
    public static void main(String[] args) {
        new MainController();
    }
}
