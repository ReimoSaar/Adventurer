import java.awt.*;
import java.awt.event.ActionListener;

public class Bar {
    private float width;
    private float height;
    private float x;
    private float y;
    private Color mainColor;
    private Color backgroundColor;
    private float variableMax;
    private float variableChange;

    public Bar(int variableMax, int variableChange, Color mainColor, Color backgroundColor, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.variableMax = variableMax;
        this.variableChange = variableChange;
        this.mainColor = mainColor;
        this.backgroundColor = backgroundColor;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(backgroundColor);
        g2.fillRect((int)x, (int)y, (int)width, (int) height);
        g2.setColor(mainColor);
        float rectangleWidth = width / variableMax;
        g2.fillRect((int )x, (int) y, (int) (rectangleWidth * variableChange), (int) height);
    }

    public void drawImprovedBar(Graphics2D g2) {
        g2.setColor(backgroundColor);
        g2.fillRect((int)x, (int)y, (int)width, (int) height);
        g2.setColor(mainColor);
        float rectangleWidth = width * 0.5f / variableMax;
        float rectangleX = x + width * 0.5f;
        g2.fillRect((int )rectangleX, (int) y, (int) (rectangleWidth * variableChange), (int) height);
        rectangleX = x + ((width * 0.5f / variableMax) * (variableMax - variableChange));
        rectangleWidth = (float) Math.ceil(width * 0.5f / variableMax * variableChange);
        g2.fillRect((int) rectangleX, (int) y, (int) (rectangleWidth), (int) height);
    }
}
