import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.Buffer;

public class Character {
    private BufferedImage image;
    private Rectangle rectangle;
    private float standingLocation;
    private float xVel;
    private float yVel;
    private float x;
    private float y;
    private BufferedImage standing;
    private BufferedImage shooting;
    private Animation right;
    private Animation left;
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = resolution.width;
    private int screenHeight = resolution.height;
    private BufferedImage standingImg;
    private float movementRadius;
    private int health;
    public int counter = 0;
    private BufferedImage currentImage;
    private float change = 0.05f * screenWidth;
    public float animChangeDistance;
    public boolean hasCrossedACD = false;
    private boolean isFacingLeft;
    private Bullet bullet;
    private BufferedImageLoader loader;
    private int shootTime = 0;
    private int reactionTime;

    private int maxHealth;
    private Bar healthBar;

    private Platform platform;
    private AudioPlayer shootingSound;

    public Character(BufferedImage standing, BufferedImage shooting, Animation right, Animation left, float x, float y, float width, float height, int health) {
        this.standing = standing;
        this.shooting = shooting;
        this.right = right;
        this.left = left;
        this.x = screenWidth * x;
        this.y = screenHeight * y;
        x = screenWidth * x;
        y = screenHeight * y;
        width = Math.round(screenWidth * width);
        height = Math.round(screenHeight * height);
        this.health = health;
        standingLocation = this.x;
        animChangeDistance = this.x;
        this.rectangle = new Rectangle((int) x, (int) y, (int) width, (int) height);
        platform = new Platform();
    }

    public Character(BufferedImage standing, BufferedImage shooting, Animation right, Animation left,
                     float x, float y, float width, float height, float xVel, float movementRadius, int health, int reactionTime) {
        this.standing = standing;
        this.shooting = shooting;
        this.right = right;
        this.left = left;
        x = screenWidth * x;
        this.x = x;
        y = screenHeight * y;
        this.y = screenHeight * y;
        xVel = Math.round(screenWidth * xVel);
        this.xVel = xVel;
        movementRadius = screenWidth * movementRadius;
        this.movementRadius = movementRadius;
        standingLocation = this.x;
        animChangeDistance = this.x;
        width = Math.round(screenWidth * width);
        height = Math.round(screenHeight * height);
        this.health = health;
        maxHealth = health;
        this.reactionTime = reactionTime;
        this.rectangle = new Rectangle((int) x, (int) y, (int) width, (int) height);
        platform = new Platform();
        bullet = new Bullet();
        loader = new BufferedImageLoader();
        shootingSound = new AudioPlayer("SFX/shoot.wav");
    }

    public void setMaxHealth(int health) {
        maxHealth = health;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public BufferedImage getImage() {
        return this.image;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public void move(int x, int y) {
        this.rectangle.setBounds(x, y, rectangle.width, rectangle.height);
    }

    public boolean intersects(Rectangle rectangle) {
        return this.rectangle.intersects(rectangle);
    }

    public Rectangle intersection(Rectangle rectangle) {
        return this.rectangle.intersection(rectangle);
    }

    public void setxVel(float xVel) {
        this.xVel = xVel;
    }

    public float getxVel() {
        return xVel;
    }

    public void setyVel(float yVel) {
        this.yVel = yVel;
    }

    public float getyVel() {
        return yVel;
    }

    public float getMovementRadius() {
        return movementRadius;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStandingLocation(float standingLocation) {
        this.standingLocation = standingLocation * screenWidth;
    }

    public float getStandingLocation() {
        return (float) (standingLocation / screenWidth);
    }

    public void moveCharacter() {
        rectangle.x += platform.getxVel() + xVel;
        standingLocation += platform.getxVel();
        animChangeDistance += platform.getxVel();
        rectangle.y += yVel;
        for (RectangleImage platforms : platform.getPlatformLevels()) {
            if (getRectangle().intersects(platforms.getRectangle())) {

                //on platform interaction
                if (rectangle.y + rectangle.height < platforms.getRectangle().y + platforms.getRectangle().height / 2 &&
                        rectangle.y + rectangle.height < platforms.getRectangle().y + rectangle.height / 4 &&
                        yVel >= 0) {
                    yVel = 0;
                    rectangle.y = platforms.getRectangle().y - rectangle.height;
                }

                //bottom platform interaction
                if (rectangle.y > platforms.getRectangle().y + platforms.getRectangle().height / 2) {
                    if (rectangle.x + rectangle.width > platforms.getRectangle().x &&
                            rectangle.x < platforms.getRectangle().x + platforms.getRectangle().width &&
                            yVel <= 0) {
                        yVel = 0;
                        rectangle.y = platforms.getRectangle().y + platforms.getRectangle().height;
                    }
                }

                //left platform interaction
                if (rectangle.x + rectangle.width < platforms.getRectangle().x + platforms.getRectangle().width / 2) {
                    if (rectangle.y + rectangle.height > platforms.getRectangle().y &&
                            rectangle.y < platforms.getRectangle().y + platforms.getRectangle().height) {
                        rectangle.x = platforms.getRectangle().x - rectangle.width;
                    }
                }

                //right platform interaction
                if (rectangle.x > platforms.getRectangle().x + platforms.getRectangle().width / 2) {
                    if (rectangle.y + rectangle.height > platforms.getRectangle().y &&
                            rectangle.y < platforms.getRectangle().y + platforms.getRectangle().height) {
                        rectangle.x = platforms.getRectangle().x + platforms.getRectangle().width;
                    }
                }
            }
        }
        yVel += Constants.GRAVITY * screenHeight;
    }

    public void movePlayer(float xVel) {
        rectangle.x += xVel;
        standingLocation += platform.getxVel();
        animChangeDistance += platform.getxVel();
    }

    public void drawPlayerStanding(Graphics2D g2, ActionListener imageObserver) {
        g2.drawImage(standing, rectangle.x, rectangle.y, rectangle.width, rectangle.height, (ImageObserver) imageObserver);
    }

    public float getACD() {
        return animChangeDistance;
    }

    public void setACD(float ACD) {
        animChangeDistance = ACD;
    }

    public void changePlayerImg(float xVel, float pXVel) {
        if (xVel == 0) {
            currentImage = standing;
        }
        if (xVel > 0 || pXVel < 0) {
            if (rectangle.x >= getACD()) {
                setACD(animChangeDistance + change);
                counter++;
            }
            switch (counter) {
                case 0:
                    currentImage = right.getImage1();
                    break;
                case 1:
                    currentImage = right.getImage2();
                    break;
                case 2:
                    currentImage = right.getImage3();
                    break;
                case 3:
                    counter = 0;
                    break;
            }
        }
        if (xVel < 0) {
            if (rectangle.x <= getACD()) {
                setACD(animChangeDistance -= change);
                counter++;
            }
            switch (counter) {
                case 0:
                    currentImage = left.getImage1();
                    break;
                case 1:
                    currentImage = left.getImage2();
                    break;
                case 2:
                    currentImage = left.getImage3();
                    break;
                case 3:
                    counter = 0;
                    break;
            }
        }
    }

    public void changeCharacterImg() {
        if (xVel == 0) {
            currentImage = standing;
        }
        if (xVel > 0) {
            if (rectangle.x >= getACD()) {
                setACD(animChangeDistance + change);
                counter++;
            }
            switch (counter) {
                case 0:
                    currentImage = right.getImage1();
                    break;
                case 1:
                    currentImage = right.getImage2();
                    break;
                case 2:
                    currentImage = right.getImage3();
                    break;
                case 3:
                    counter = 0;
                    break;
            }
        }
        if (xVel < 0) {
            if (rectangle.x <= getACD()) {
                setACD(animChangeDistance - change);
                counter++;
            }
            switch (counter) {
                case 0:
                    currentImage = left.getImage1();
                    break;
                case 1:
                    currentImage = left.getImage2();
                    break;
                case 2:
                    currentImage = left.getImage3();
                    break;
                case 3:
                    counter = 0;
                    break;
            }
        }
    }

    public void drawCharacter(Graphics g2, ActionListener imageObserver) {
        g2.drawImage(currentImage, rectangle.x, rectangle.y, rectangle.width, rectangle.height, (ImageObserver) imageObserver);
    }

    public void addHealthBar() {
        healthBar = new Bar(this.maxHealth, this.health, Color.RED, Color.WHITE, rectangle.x, (float) (rectangle.y - 0.02 * screenHeight),
                rectangle.width, (float) (0.005 * screenWidth));
    }

    public void drawHealthBar(Graphics g2) {
        healthBar.draw((Graphics2D) g2);
    }

    public void changeCharacterDirection() {
        if (getRectangle().x > standingLocation + movementRadius) {
            xVel = -xVel;
        }
        if (getRectangle().x < standingLocation - movementRadius) {
            xVel = -xVel;
        }
    }

    public boolean canShoot() {
        if (isFacingLeft && Player.getPlayer().getRectangle().x < rectangle.x ||
                !isFacingLeft && Player.getPlayer().getRectangle().x > rectangle.x) {
            if (rectangle.y + rectangle.height - 2 * rectangle.height / 3 >= Player.getPlayer().rectangle.y
                    && rectangle.y + rectangle.height / 3 <= Player.getPlayer().rectangle.y + Player.getPlayer().rectangle.height) {
                return true;
            }
        }
        return false;
    }

    public void loseHealth() {
        health--;
        if (health < 0) {
            health = 0;
        }
    }

    public void addShooting(String path, float width, float height, float xVel, float yVel, boolean friendly) {
        if (this.xVel > 0) {
            isFacingLeft = false;
        }
        if (this.xVel < 0) {
            isFacingLeft = true;
        }
        shootTime++;
        int reactionCounter;
        if (shootTime > 100) {
            if (!canShoot()) {
                shootTime = 100;
            } else {
                shootTime = 0;
            }
        }
        if (shootTime == 0) {
            shootingSound.play();
            if (isFacingLeft) {
                bullet.addBullet(new Bullet(loader.loadBufferedImage(path), rectangle.x + rectangle.width,
                        rectangle.y + rectangle.height / 3.0f, width, height, -xVel, yVel, friendly));
            }
            if (!isFacingLeft) {
                bullet.addBullet(new Bullet(loader.loadBufferedImage(path), rectangle.x + rectangle.width,
                        rectangle.y + rectangle.height / 3.0f, width, height, xVel, yVel, friendly));
            }
        }
    }

    public void addJumping() {
        if (canJump() && isJumpAvailable()) {
            yVel = screenHeight * Constants.JUMP_POWER;
        }
    }

    private boolean canJump() {
        for (RectangleImage platform : platform.getPlatformLevels()) {
            if (rectangle.x + rectangle.width > platform.getRectangle().x && rectangle.x < platform.getRectangle().x + platform.getRectangle().width &&
                    rectangle.y + rectangle.height == platform.getRectangle().y) {
                if (rectangle.x > platform.getRectangle().x + platform.getRectangle().width - rectangle.width && xVel > 0 ||
                        rectangle.x < platform.getRectangle().x && xVel < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isJumpAvailable() {
        for (RectangleImage platforms : platform.getPlatformLevels()) {
            if (rectangle.y == (int) (platforms.getRectangle().y - rectangle.getHeight())) {
                return true;
            }
        }
        return false;
    }
}
