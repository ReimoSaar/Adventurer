
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends JPanel implements KeyListener, ActionListener {
    public static boolean loadPlayer;
    private static Character player;
    private Platform platform;
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

    private BufferedImageLoader loader;
    private SpriteSheet spriteSheet;
    private BufferedImage playerSprite;
    private BufferedImage[] playerImages = new BufferedImage[7];
    private Animation playerRightAnimation;
    private Animation playerLeftAnimation;
    private Bullet bullet;
    private LevelStateManager levelState;
    private EnemyBot enemyBot;
    private Save save;
    private PauseMenu pauseMenu;
    private GameStateManager gameState;
    private AudioPlayer shootingSound;
    private Bar healthBar;

    public Player() {
        loader = new BufferedImageLoader();
        playerSprite = loader.loadBufferedImage("player_sprite");
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

        platform = new Platform();
        bullet = new Bullet();
        levelState = new LevelStateManager();
        enemyBot = new EnemyBot();
        save = new Save();
        pauseMenu = new PauseMenu();
        gameState = new GameStateManager();
    }

    public void renderPlayer(Graphics g) {
        new Text(g, player.getHealth() + "", 0.1f, 0.6f, 30, "arial", Color.RED, 1);
        Graphics2D g2 = (Graphics2D) g;
        for (Bullet bullet : bullet.getBullet()) {
            bullet.drawBullet(g2, this);
        }
        player.drawCharacter(g2, this);
        healthBar = new Bar(Constants.PLAYER_MAX_HEALTH, player.getHealth(), Color.RED, Color.WHITE,
                0.4f * screenWidth, 0.8f * screenHeight, 0.2f * screenWidth, 0.05f * screenHeight);
        healthBar.drawImprovedBar(g2);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameState.getState() == GameState.IN_GAME) {
            Player.loadPlayer = false;
        }
        player.getRectangle().y += playeryVel;
        player.movePlayer(playerxVel);

        for (RectangleImage platforms : platform.getPlatformLevels()) {
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
            platform.setxVel(-Constants.PLAYER_SPEED);
        } else {
            platform.setxVel(0.0f);
        }

        if (shootingButtonDown && isShootingAvailable) {
            shootingSound.stop();
            shootingSound.play();
            shootingSound.getClip().setFramePosition(0);
            if (!isFacingLeft) {
                bullet.addBullet(new Bullet(loader.loadBufferedImage("bullet"), player.getRectangle().x + player.getRectangle().width,
                        player.getRectangle().y + player.getRectangle().height / 3.0f,
                        0.005f, 0.005f, 0.007f, 0.0f, true));
            }
            if (isFacingLeft) {
                bullet.addBullet(new Bullet(loader.loadBufferedImage("bullet"), player.getRectangle().x,
                        player.getRectangle().y + player.getRectangle().height / 3.0f,
                        0.005f, 0.005f, -0.007f, 0.0f, true));
            }
            isShootingAvailable = false;
        }


        player.changePlayerImg(playerxVel, platform.getxVel());

        checkBulletDamage();

        save.setPlayerLocation(player.getRectangle().x, player.getRectangle().y);

        if (player.getHealth() == 0 || player.getRectangle().y > screenHeight) {
            gameState.setState(GameState.DEATH_MENU);
        }
    }

    public void preparePlayerObjects() {
        bullet.removeAllBullets();
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

    private boolean isJumpAvailable() {
        for (RectangleImage platforms : platform.getPlatformLevels()) {
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
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
                if (isJumpAvailable()) {
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

    private void checkBulletDamage() {
        for (Bullet bullets : bullet.getBullet()) {
            if (bullets.getRectangle().intersects(player.getRectangle()) && !bullets.isFriendlyBullet()) {
                player.loseHealth();
            }
        }
        bullet.getBullet().removeIf(bullet -> bullet.getRectangle().intersects(player.getRectangle()) &&
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
