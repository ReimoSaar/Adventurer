import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader {
    BufferedImage image;


    public BufferedImage loadBufferedImage(String path) {
        try {
            image = ImageIO.read(BufferedImageLoader.class.getResource(path + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
