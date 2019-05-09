package game.characters;

import game.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Character {
    @Getter
    private BufferedImage image;
    @Getter
    private Rectangle rectangle;
    @Getter
    @Setter
    private float standingLocation;
    @Getter
    private float xVel;
    @Getter
    private float yVel;
    @Getter
    private float x;
    @Getter
    private float y;
    private BufferedImage standing;
    private BufferedImage shooting;
    private Animation right;
    private Animation left;
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = resolution.width;
    private int screenHeight = resolution.height;
    private BufferedImage standingImg;
    @Getter
    private float movementRadius;
    @Getter
    @Setter
    private int health;
    public int counter = 0;
    private BufferedImage currentImage;
    private float change = 0.05f * screenWidth;
    @Getter
    @Setter
    public float animChangeDistance;
    public boolean hasCrossedACD = false;
    @Getter
    @Setter
    private boolean isFacingLeft;
    @Setter
    @Getter
    private int shootTime = 0;
    private int reactionTime;
    @Setter
    @Getter
    private int maxHealth;
    private Bar healthBar;

    @Getter
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
        shootingSound = new AudioPlayer("SFX/shoot.wav");
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

    public void setStandingLocation(float standingLocation) {
        this.standingLocation = standingLocation * screenWidth;
    }

    public float getStandingLocation() {
        return (float) (standingLocation / screenWidth);
    }

    public void moveCharacter(Game game) {
        rectangle.x += game.getPlatform().getxVel() + xVel;
        standingLocation += game.getPlatform().getxVel();
        animChangeDistance += game.getPlatform().getxVel();
        rectangle.y += yVel;
        for (RectangleImage platforms : game.getPlatform().getPlatformLevels(game.getLevelState())) {
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
        } else if (xVel < 0) {
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

    public void draw(Graphics g2, ImageObserver imageObserver) {
        g2.drawImage(currentImage, rectangle.x, rectangle.y, rectangle.width, rectangle.height, imageObserver);
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

    public void loseHealth() {
        health--;
        if (health < 0) {
            health = 0;
        }
    }

    public void addJumping(Game game) {
        if (canJump(game) && isJumpAvailable(game)) {
            yVel = screenHeight * Constants.JUMP_POWER;
        }
    }

    private boolean canJump(Game game) {
        for (RectangleImage platforms : game.getPlatform().getPlatformLevels(game.getLevelState())) {
            if (rectangle.x + rectangle.width > platforms.getRectangle().x && rectangle.x < platforms.getRectangle().x + platforms.getRectangle().width &&
                    rectangle.y + rectangle.height == platforms.getRectangle().y) {
                if (rectangle.x > platforms.getRectangle().x + platforms.getRectangle().width - rectangle.width && xVel > 0 ||
                        rectangle.x < platforms.getRectangle().x && xVel < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isJumpAvailable(Game game) {
        for (RectangleImage platforms : game.getPlatform().getPlatformLevels(game.getLevelState())) {
            if (rectangle.y == (int) (platforms.getRectangle().y - rectangle.getHeight())) {
                return true;
            }
        }
        return false;
    }

    public void incrementShootTime() {
        shootTime++;
    }
}
