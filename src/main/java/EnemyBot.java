import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class EnemyBot extends JPanel implements ActionListener {
    private static ArrayList<Character> enemies;
    private static ArrayList<Character> level2Enemies;
    private static ArrayList<Character> level3Enemies;
    private static ArrayList<Character> level4Enemies;
    private static ArrayList<Character> level5Enemies;
    private static ArrayList<Character> level6Enemies;
    private static ArrayList<Character> level7Enemies;
    private static ArrayList<Character> level8Enemies;
    private static ArrayList<Character> level9Enemies;
    private Platform platform;
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenWidth = resolution.width;
    private double screenHeight = resolution.height;
    private double enemyBotX;
    private double enemyBotY;
    private int platformHeight;
    private FPS fps;
    private LevelStateManager levelState;
    private BufferedImageLoader loader;
    private Bullet bullet;
    private SpriteSheet[] spriteSheet = new SpriteSheet[9];
    private BufferedImage[] enemySprite = new BufferedImage[9];
    private BufferedImage[] enemyImages1 = new BufferedImage[7];
    private BufferedImage[] enemyImages2 = new BufferedImage[7];
    private Animation[] enemyRightAnimation = new Animation[9];
    private Animation[] enemyLeftAnimation = new Animation[9];

    public EnemyBot() {
        platform = new Platform();
        fps = new FPS();
        levelState = new LevelStateManager();
        loader = new BufferedImageLoader();
        bullet = new Bullet();
        enemySprite[0] = loader.loadBufferedImage("bots/enemy_sprite_1");
        enemySprite[1] = loader.loadBufferedImage("bots/enemy_sprite_2");
        spriteSheet[0] = new SpriteSheet(enemySprite[0]);
        spriteSheet[1] = new SpriteSheet(enemySprite[1]);
        //right movement
        enemyImages1[0] = spriteSheet[0].grabImage(1, 2, 32, 32);
        enemyImages1[1] = spriteSheet[0].grabImage(2, 2, 32, 32);
        enemyImages1[2] = spriteSheet[0].grabImage(3, 2, 32, 32);

        enemyImages2[0] = spriteSheet[1].grabImage(1, 2, 32, 32);
        enemyImages2[1] = spriteSheet[1].grabImage(2, 2, 32, 32);
        enemyImages2[2] = spriteSheet[1].grabImage(3, 2, 32, 32);
        //standing
        enemyImages1[3] = spriteSheet[0].grabImage(1, 1, 32, 32);
        enemyImages2[3] = spriteSheet[1].grabImage(1, 1, 32, 32);
        //left movement
        enemyImages1[4] = spriteSheet[0].grabImage(1, 3, 32, 32);
        enemyImages1[5] = spriteSheet[0].grabImage(2, 3, 32, 32);
        enemyImages1[6] = spriteSheet[0].grabImage(3, 3, 32, 32);

        enemyImages2[4] = spriteSheet[1].grabImage(1, 3, 32, 32);
        enemyImages2[5] = spriteSheet[1].grabImage(2, 3, 32, 32);
        enemyImages2[6] = spriteSheet[1].grabImage(3, 3, 32, 32);

        enemyRightAnimation[0] = new Animation(10, enemyImages1[0], enemyImages1[1], enemyImages1[2]);
        enemyRightAnimation[1] = new Animation(10, enemyImages2[0], enemyImages2[1], enemyImages2[2]);

        enemyLeftAnimation[0] = new Animation(10, enemyImages1[4], enemyImages1[5], enemyImages1[6]);
        enemyLeftAnimation[1] = new Animation(10, enemyImages2[4], enemyImages2[5], enemyImages2[6]);


        //level 1
        enemies = new ArrayList<>();
        /*level1Enemies.add(new Character(enemyImages1[3], null, enemyRightAnimation[0], enemyLeftAnimation[0],
                0.8f, 0.2f, 0.03f, 0.12f, 0.002f, 0.1f, 2));
        level1Enemies.add(new Character(enemyImages1[3], null, enemyRightAnimation[0], enemyLeftAnimation[0],
                1.0f, 0.2f, 0.03f, 0.12f, -0.004f, 0.1f, 5));
        level1Enemies.add(new Character(enemyImages1[3], null, enemyRightAnimation[0], enemyLeftAnimation[0],
                0.7f, 0.2f, 0.03f, 0.12f, 0.003f, 0.1f, 2));*/
        //level 2
        level2Enemies = new ArrayList<>();
        /*level2Enemies.add(new Character(enemyImages2[3], null, enemyRightAnimation[1], enemyLeftAnimation[1],
                0.8f, 0.2f, 0.03f, 0.12f, 0.002f, 0.01f, 2));
        level2Enemies.add(new Character(enemyImages2[3], null, enemyRightAnimation[1], enemyLeftAnimation[1],
                0.8f, 0.2f, 0.03f, 0.12f, -0.002f, 0.01f, 2));*/
        //level 3
        level3Enemies = new ArrayList<>();
        //level 4
        level4Enemies = new ArrayList<>();
        //level 5
        level5Enemies = new ArrayList<>();
        //level 6
        level6Enemies = new ArrayList<>();
        //level 7
        level7Enemies = new ArrayList<>();
        //level 8
        level8Enemies = new ArrayList<>();
        //level 9
        level9Enemies = new ArrayList<>();
    }

    public ArrayList<Character> getAllEnemyBots() {
        /*ArrayList<Character> enemyBotsList = null;
        enemyBotsList = enemies;
        switch (levelState.getState()) {
            case LEVEL_1:
                break;
            case LEVEL_2:
                enemyBotsList = level2Enemies;
                break;
            case LEVEL_3:
                enemyBotsList = level3Enemies;
                break;
            case LEVEL_4:
                enemyBotsList = level4Enemies;
                break;
            case LEVEL_5:
                enemyBotsList = level5Enemies;
                break;
            case LEVEL_6:
                enemyBotsList = level6Enemies;
                break;
            case LEVEL_7:
                enemyBotsList = level7Enemies;
                break;
            case LEVEL_8:
                enemyBotsList = level8Enemies;
                break;
            case LEVEL_9:
                enemyBotsList = level9Enemies;
                break;
        }*/
        return enemies;
    }

    public void renderEnemyBot(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Character enemyBots : getAllEnemyBots()) {
            enemyBots.drawCharacter(g2, this);
            enemyBots.addHealthBar();
            enemyBots.drawHealthBar(g2);
        }
    }

    public void prepareEnemyBot() {
            enemies.removeAll(enemies);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Character enemyBots : getAllEnemyBots()) {
            enemyBots.moveCharacter();
            enemyBots.addShooting("bullet", 0.005f, 0.005f, 0.007f, 0.0f, false);
            enemyBots.changeCharacterImg();
            enemyBots.changeCharacterDirection();
            enemyBots.addJumping();
        }
        checkBulletDamage();
    }

    public void checkBulletDamage() {
        for (Bullet bullets : bullet.getBullet()) {
            for (Character enemyBots : getAllEnemyBots()) {
                if (bullets.getRectangle().intersects(enemyBots.getRectangle()) && bullets.isFriendlyBullet()) {
                    enemyBots.loseHealth();
                }
            }
        }
        for (Character enemyBot : getAllEnemyBots()) {
            bullet.getBullet().removeIf(bullet -> bullet.getRectangle().intersects(enemyBot.getRectangle()) &&
                    bullet.isFriendlyBullet());
        }
        getAllEnemyBots().removeIf(enemyBot -> enemyBot.getHealth() == 0);
    }

    public void saveEnemyBots() {
        File outputFile;
        BufferedWriter outputWriter;

        try {
            outputFile = new File("save enemy bots.txt");
            outputWriter = new BufferedWriter(new FileWriter(outputFile));
            outputWriter.write(enemies.size() + "\n");

            for (Character enemyBots : getAllEnemyBots()) {
                outputWriter.write(Double.toString(enemyBots.getRectangle().x / screenWidth) + "\n");
                outputWriter.write(Double.toString(enemyBots.getRectangle().y / screenHeight) + "\n");
                outputWriter.write(Float.toString(enemyBots.getStandingLocation()) + "\n");
                outputWriter.write(Float.toString((float) (enemyBots.getxVel() / screenWidth)) + "\n");
                outputWriter.write(Float.toString((float) (enemyBots.getMovementRadius() / screenWidth)) + "\n");
                outputWriter.write(Integer.toString(enemyBots.getHealth()) + "\n");
                outputWriter.write(Integer.toString(enemyBots.getMaxHealth()) + "\n");
            }

            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEnemyBots() {
        prepareEnemyBot();
        File inputFile;
        BufferedReader inputReader;

        try {
            inputFile = new File("save enemy bots.txt");
            inputReader = new BufferedReader(new FileReader(inputFile));
            int botCount = Integer.parseInt(inputReader.readLine());
            for (int i = 0; i < botCount; i++) {
                float x = (float) Double.parseDouble(inputReader.readLine());
                float y = (float) (Double.parseDouble(inputReader.readLine()));
                float standX = (Float.parseFloat(inputReader.readLine()));
                float xVel = Float.parseFloat(inputReader.readLine());
                float movementRadius = Float.parseFloat(inputReader.readLine());
                int health = Integer.parseInt(inputReader.readLine());
                int maxHealth = Integer.parseInt(inputReader.readLine());
                //float ACD = (float) ((Float.parseFloat(inputReader.readLine())) * screenWidth);
                enemies.add(new Character(getStandingImage(), null, getRightAnimation(), getLeftAnimation(),
                        x, y, 0.03f, 0.12f, xVel, movementRadius, health, 0));
                enemies.get(i).setStandingLocation(standX);
                enemies.get(i).setMaxHealth(maxHealth);
            }
           /* enemies.add(new Character(getStandingImage(), null, getRightAnimation(), getLeftAnimation(),
                    0.8f, 0.2f, 0.03f, 0.12f, 0.002f, 0.1f, 2));*/
            inputReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getStandingImage() {
        BufferedImage standingImage = null;
        switch (levelState.getState()) {
            case LEVEL_1:
                standingImage = enemyImages1[3];
                break;
            case LEVEL_2:
                standingImage = enemyImages2[3];
                break;
        }
        return standingImage;
    }

    private Animation getRightAnimation() {
        Animation right = null;
        switch (levelState.getState()) {
            case LEVEL_1:
                right = enemyRightAnimation[0];
                break;
            case LEVEL_2:
                right = enemyRightAnimation[1];
                break;
        }
        return right;
    }

    private Animation getLeftAnimation() {
        Animation left = null;
        switch (levelState.getState()) {
            case LEVEL_1:
                left = enemyLeftAnimation[0];
                break;
            case LEVEL_2:
                left = enemyLeftAnimation[1];
                break;
        }
        return left;
    }

    public void addLevel1Enemies() {
        /*enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[0], enemyLeftAnimation[0],
                0.8f, 0.2f, 0.03f, 0.12f, 0.002f, 0.1f, 2));
        enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[0], enemyLeftAnimation[0],
                1.0f, 0.2f, 0.03f, 0.12f, -0.004f, 0.1f, 5));
        enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[0], enemyLeftAnimation[0],
                0.7f, 0.2f, 0.03f, 0.12f, 0.003f, 0.1f, 2));*/
    }

    public void addLevel2Enemies() {
        enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[1], enemyLeftAnimation[1],
                0.75f, 0.2f, 0.03f, 0.12f, 0.003f, 0.35f, 2, 0));
        enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[1], enemyLeftAnimation[1],
                0.80f, 0.2f, 0.03f, 0.12f, -0.003f, 0.32f, 10, 0));
    }

    public void addEnemies() {
        if (levelState.getState() == LevelState.LEVEL_1) {
            addLevel1Enemies();
        }
        if (levelState.getState() == LevelState.LEVEL_2) {
            addLevel2Enemies();
        }
    }
}
