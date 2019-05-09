package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader {

    private BufferedImageLoader() {

    }

    public static BufferedImage loadBufferedImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(BufferedImageLoader.class.getResource("/" + path + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
