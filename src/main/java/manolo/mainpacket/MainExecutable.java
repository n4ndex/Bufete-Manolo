
/**
 * MainExecutable - Entry point for the MainPacket application.
 *
 * Version: 1.0
 * Authors: Diego Fernández Rojo, David Maestre Díaz, Hugo Villodres Moreno, Isaac Requena Santiago
 *
 * This class initializes the MainController, which serves as the main
 * controller for the application.
 */

package manolo.mainpacket;

import manolo.mainpacket.controller.MainController;

public class MainExecutable {
    public static void main(String[] args) {
        new MainController();
    }
}
