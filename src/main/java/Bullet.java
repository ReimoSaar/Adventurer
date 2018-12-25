import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Bullet implements ActionListener {
    private BufferedImage image;
    private Rectangle rectangle;
    private float xVel;
    private float yVel;
    private float x;
    private float y;
    private boolean isFriendlyBullet;
    private float width;
    private float height;
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = resolution.width;
    private int screenHeight = resolution.height;
    private static ArrayList<Bullet> bullets = new ArrayList<>();
    private Platform platform;
    private LevelStateManager levelState;
    public GameStateManager gameState;
    private EnemyBot enemyBot;

    public Bullet() {
        platform = new Platform();
        levelState = new LevelStateManager();
        gameState = new GameStateManager();
    }

    public Bullet(BufferedImage image, float x, float y, float width, float height, float xVel, float yVel, boolean isFriendlyBullet) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        width = Math.round(resolution.width * width);
        height = Math.round(resolution.height * height);
        this.xVel = Math.round(resolution.width * xVel);
        this.yVel = Math.round(resolution.height * yVel);
        this.isFriendlyBullet = isFriendlyBullet;
        this.rectangle = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public int getBulletCount() {
        return bullets.size();
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public Image getImage() {
        return this.image;
    }

    public Boolean isFriendlyBullet() {
        return isFriendlyBullet;
    }

    public ArrayList<Bullet> getBullet() {
        return bullets;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public void move(int x, int y) {
        this.rectangle.setBounds(x, y, rectangle.width, rectangle.height);
    }

    public void drawBullet(Graphics2D g2, ActionListener imageObserver) {
        g2.drawImage(this.image, this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height, (ImageObserver) imageObserver);
    }

    public boolean intersects(Rectangle rectangle) {
        return this.rectangle.intersects(rectangle);
    }

    public Rectangle intersection(Rectangle rectangle) {
        return this.rectangle.intersection(rectangle);
    }

    public void detectCollision() {
        for (RectangleImage platform : platform.getPlatformLevels()) {
            getBullet().removeIf(bullet -> bullet.rectangle.intersects(platform.getRectangle()) ||
                    bullet.rectangle.x > screenWidth || bullet.rectangle.x < 0);
        }
    }

    public void removeAllBullets() {
        getBullet().removeAll(bullets);
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < getBulletCount(); i++) {
            bullets.get(i).getRectangle().x += platform.getxVel() + bullets.get(i).getXVelocity();
        }
        detectCollision();
    }
}
