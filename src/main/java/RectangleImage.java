import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

public class RectangleImage {
    private Image image;
    private Rectangle rectangle;
    private float xVel;
    private float yVel;
    private float x;
    private float y;


    public RectangleImage(Image image, float x, float y, float width, float height) {
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        this.image = image;
        this.x = x * resolution.width;
        this.y = y * resolution.height;
        x = resolution.width * x;
        y = resolution.height * y;
        width = Math.round(resolution.width * width);
        height = Math.round(resolution.height * height);
        this.rectangle = new Rectangle((int) x,(int) y, (int) width, (int) height);
    }

    public float getXVelocity() {
        return this.xVel;
    }
    public float getYVelocity() {
        return this.yVel;
    }

    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public void move(int x, int y) {
        this.rectangle.setBounds(x, y, rectangle.width, rectangle.height);
    }

    public void draw(Graphics2D g2, ActionListener imageObserver) {
        g2.drawImage(this.image, this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height, (ImageObserver) imageObserver);
    }

    public boolean intersects(Rectangle rectangle) {
        return this.rectangle.intersects(rectangle);
    }

    public Rectangle intersection(Rectangle rectangle) {
        return this.rectangle.intersection(rectangle);
    }

}
