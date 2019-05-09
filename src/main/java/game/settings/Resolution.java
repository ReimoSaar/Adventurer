package game.settings;

import java.awt.*;

public class Resolution {

    private static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();

    public static float screenWidth = resolution.width;
    public static float screenHeight = resolution.height;
}
