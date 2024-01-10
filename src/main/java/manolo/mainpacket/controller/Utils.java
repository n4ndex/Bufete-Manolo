package manolo.mainpacket.controller;

import java.nio.file.Path;

public class Utils {
    public static boolean isLevelsDeep(Path path, int levelsToCheck) {
        int levels = path.getNameCount();
        return levels == levelsToCheck;
    }
}
