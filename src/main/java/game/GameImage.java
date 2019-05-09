package game;

import java.awt.*;
import java.net.URL;

public class GameImage {
    public static Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Game.class.getResource("/" + path + ".png");
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            System.out.println("An error has occured -" + e.getMessage());
        }
        return tempImage;
    }
}
