
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public static boolean loadPlayer;
    private static Character player;
    private float playerxVel;
    private static float playeryVel;
    private int platformHeight;
    private int platformLength;
    Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = resolution.width;
    private int screenHeight = resolution.height;
    private boolean rightKeyDown = false;
    private boolean leftKeyDown = false;
    private boolean shootingButtonDown = false;
    private boolean isShootingAvailable = true;
    private boolean isFacingLeft;

    private boolean canMove = true;

    private SpriteSheet spriteSheet;
    private BufferedImage playerSprite;
    private BufferedImage[] playerImages = new BufferedImage[7];
    private Animation playerRightAnimation;
    private Animation playerLeftAnimation;
    private Save save;
    private AudioPlayer shootingSound;
    private Bar healthBar;

    public Player() {
        playerSprite = BufferedImageLoader.loadBufferedImage("player_sprite");
        spriteSheet = new SpriteSheet(playerSprite);
        //right movement
        playerImages[0] = spriteSheet.grabImage(1, 2, 32, 32);
        playerImages[1] = spriteSheet.grabImage(2, 2, 32, 32);
        playerImages[2] = spriteSheet.grabImage(3, 2, 32, 32);
        //standing
        playerImages[3] = spriteSheet.grabImage(1, 1, 32, 32);
        //left movement
        playerImages[4] = spriteSheet.grabImage(1, 3, 32, 32);
        playerImages[5] = spriteSheet.grabImage(2, 3, 32, 32);
        playerImages[6] = spriteSheet.grabImage(3, 3, 32, 32);
        playerRightAnimation = new Animation(10, playerImages[0], playerImages[1], playerImages[2]);
        playerLeftAnimation = new Animation(10, playerImages[4], playerImages[5], playerImages[6]);
        player = new Character(playerImages[3], null, playerRightAnimation, playerLeftAnimation, 0.4f, 0.3f, 0.03f, 0.12f, Constants.PLAYER_MAX_HEALTH);
        shootingSound = new AudioPlayer("SFX/shoot.wav");

        save = new Save();
    }

    public void renderPlayer(Graphics g, ImageObserver imageObserver) {
        new Text(g, player.getHealth() + "", 0.1f, 0.6f, 30, "arial", Color.RED, 1);
        Graphics2D g2 = (Graphics2D) g;
        player.drawCharacter(g2, imageObserver);
        healthBar = new Bar(Constants.PLAYER_MAX_HEALTH, player.getHealth(), Color.RED, Color.WHITE,
                0.4f * screenWidth, 0.8f * screenHeight, 0.2f * screenWidth, 0.05f * screenHeight);
        healthBar.drawImprovedBar(g2);
    }

    public void update(Game game) {
        if (game.getGameState().getState() == GameState.IN_GAME) {
            Player.loadPlayer = false;
        }
        player.getRectangle().y += playeryVel;
        player.movePlayer(playerxVel, game.getPlatform());

        for (RectangleImage platforms : game.getPlatform().getPlatformLevels(game.getLevelState())) {
            platformHeight = (int) (platforms.getRectangle().y - player.getRectangle().getHeight());
            platformLength = (platforms.getRectangle().x);
            if (player.intersects(platforms.getRectangle())) {

                //on platform interaction
                if (player.getRectangle().y + player.getRectangle().height < platforms.getRectangle().y + platforms.getRectangle().height / 2 &&
                        player.getRectangle().y + player.getRectangle().height < platforms.getRectangle().y + player.getRectangle().height / 4 &&
                playeryVel >= 0) {
                        playeryVel = 0;
                        player.getRectangle().y = platforms.getRectangle().y - player.getRectangle().height;
                }

                //bottom platform interaction
                if (player.getRectangle().y > platforms.getRectangle().y + platforms.getRectangle().height / 2) {
                    if (player.getRectangle().x + player.getRectangle().width > platforms.getRectangle().x &&
                            player.getRectangle().x < platforms.getRectangle().x + platforms.getRectangle().width &&
                    playeryVel <= 0) {
                        playeryVel = 0;
                        player.getRectangle().y = platforms.getRectangle().y + platforms.getRectangle().height;
                    }
                }

                //left platform interaction
                if (player.getRectangle().x + player.getRectangle().width < platforms.getRectangle().x + platforms.getRectangle().width / 2) {
                    if (player.getRectangle().y + player.getRectangle().height > platforms.getRectangle().y &&
                            player.getRectangle().y < platforms.getRectangle().y + platforms.getRectangle().height) {
                        player.getRectangle().x = platforms.getRectangle().x - player.getRectangle().width;
                    }
                }

                //right platform interaction
                if (player.getRectangle().x > platforms.getRectangle().x + platforms.getRectangle().width / 2) {
                    if (player.getRectangle().y + player.getRectangle().height > platforms.getRectangle().y &&
                            player.getRectangle().y < platforms.getRectangle().y + platforms.getRectangle().height) {
                        player.getRectangle().x = platforms.getRectangle().x + platforms.getRectangle().width;
                    }
                }
            }
        }

        if (leftKeyDown) {
            moveLeft();
        }

        if (rightKeyDown) {
            moveRight();
        }

        playeryVel += screenHeight * Constants.GRAVITY;

        if (isMovingRadiusOver()) {
            game.getPlatform().setxVel(-Constants.PLAYER_SPEED);
        } else {
            game.getPlatform().setxVel(0.0f);
        }

        if (shootingButtonDown && isShootingAvailable) {
            shootingSound.stop();
            shootingSound.play();
            shootingSound.getClip().setFramePosition(0);
            if (!isFacingLeft) {
                game.getBullets().add((new Bullet(BufferedImageLoader.loadBufferedImage("bullet"), player.getRectangle().x + player.getRectangle().width,
                        player.getRectangle().y + player.getRectangle().height / 3.0f,
                        0.005f, 0.005f, 0.007f, 0.0f, true)));
            }
            if (isFacingLeft) {
                game.getBullets().add(new Bullet(BufferedImageLoader.loadBufferedImage("bullet"), player.getRectangle().x,
                        player.getRectangle().y + player.getRectangle().height / 3.0f,
                        0.005f, 0.005f, -0.007f, 0.0f, true));
            }
            isShootingAvailable = false;
        }


        player.changePlayerImg(playerxVel, game.getPlatform().getxVel());

        checkBulletDamage(game.getBullets());

        save.setPlayerLocation(player.getRectangle().x, player.getRectangle().y);

        if (player.getHealth() == 0 || player.getRectangle().y > screenHeight) {
            game.getGameState().setState(GameState.DEATH_MENU);
        }
    }

    public void preparePlayerObjects() {
        player.getRectangle().x = (int) player.getX();
        player.getRectangle().y = (int) player.getY();
        player.setHealth(Constants.PLAYER_MAX_HEALTH);
        playeryVel = 0;
    }

    public void loadPlayer() {
        if (loadPlayer) {
            player.getRectangle().x = (int) (Save.saveInformation[Save.pXLoc] * screenWidth);
            player.getRectangle().y = (int) (Save.saveInformation[Save.pYLoc] * screenHeight);
        }
    }

    private boolean isJumpAvailable(Game game) {
        for (RectangleImage platforms : game.getPlatform().getPlatformLevels(game.getLevelState())) {
            if (player.getRectangle().y == (int) (platforms.getRectangle().y - player.getRectangle().getHeight())) {
                return true;
            }
        }
        return false;
    }

    private boolean isMovingRadiusOver() {
        if (player.getRectangle().x >= screenWidth * 0.5f && rightKeyDown) {
            return true;
        }
        return false;
    }

    public void keyPressed(KeyEvent e, Game game) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                isFacingLeft = false;
                rightKeyDown = true;
                break;
            case KeyEvent.VK_A:
                isFacingLeft = true;
                leftKeyDown = true;
                break;
            case KeyEvent.VK_W:
                if (isJumpAvailable(game)) {
                    jump();
                }
                break;
            case KeyEvent.VK_SPACE:
                shootingButtonDown = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                rightKeyDown = false;
                playerxVel = 0;
                break;
            case KeyEvent.VK_A:
                leftKeyDown = false;
                playerxVel = 0;
                break;
            case KeyEvent.VK_SPACE:
                shootingButtonDown = false;
                isShootingAvailable = true;
                break;
        }
    }

    private void checkBulletDamage(ArrayList<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (bullet.getRectangle().intersects(player.getRectangle()) && !bullet.isFriendlyBullet()) {
                player.loseHealth();
            }
        }
        bullets.removeIf(bullet -> bullet.getRectangle().intersects(player.getRectangle()) &&
                !bullet.isFriendlyBullet());
    }

    public void moveRight() {
        int xRadius = (int) (resolution.width * 0.5f);
        if (player.getRectangle().x >= xRadius) {
            player.getRectangle().x = xRadius;
        }
        if (canMove) {
            playerxVel = Math.round(screenWidth * Constants.PLAYER_SPEED);
        } else {
            playerxVel = 0;
        }
    }

    public void moveLeft() {
        if (player.getRectangle().x <= 0) {
            player.getRectangle().x = 0;
        }
        if (canMove) {
            playerxVel = Math.round(screenWidth * -Constants.PLAYER_SPEED);
        } else {
            playerxVel = 0;
        }
    }

    public void jump() {
        playeryVel = screenHeight * Constants.JUMP_POWER;
    }

    public static Character getPlayer() {
        return player;
    }
}
