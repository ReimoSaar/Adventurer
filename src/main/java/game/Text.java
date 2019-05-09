package game;

import java.awt.*;

public class Text {

    public Text(Graphics g, String string, float x, float y, float size, String font, Color color, int style) {
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        float screenWidth = resolution.width;
        float screenHeight = resolution.height;
        x = screenWidth * x;
        y = screenHeight * y;
        size = screenWidth / screenHeight * size;
        Font stringFont = new Font(font, style, (int) size);
        g.setFont(stringFont);
        g.setColor(color);
        g.drawString(string, (int) x, (int) y);
    }
}
