
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.awt.image.ImageObserver;
import java.io.*;
import java.util.ArrayList;

public class EnemyBot {
    private static ArrayList<Character> enemies;
    private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private double screenWidth = resolution.width;
    private double screenHeight = resolution.height;
    private double enemyBotX;
    private double enemyBotY;
    private int platformHeight;
    private SpriteSheet[] spriteSheet = new SpriteSheet[9];
    private BufferedImage[] enemySprite = new BufferedImage[9];
    private BufferedImage[] enemyImages1 = new BufferedImage[7];
    private BufferedImage[] enemyImages2 = new BufferedImage[7];
    private Animation[] enemyRightAnimation = new Animation[9];
    private Animation[] enemyLeftAnimation = new Animation[9];

    public EnemyBot() {
        enemySprite[0] = BufferedImageLoader.loadBufferedImage("bots/enemy_sprite_1");
        enemySprite[1] = BufferedImageLoader.loadBufferedImage("bots/enemy_sprite_2");
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

    public void renderEnemyBot(Graphics g, ImageObserver imageObserver) {
        Graphics2D g2 = (Graphics2D) g;
        for (Character enemyBots : getAllEnemyBots()) {
            enemyBots.drawCharacter(g2, imageObserver);
            enemyBots.addHealthBar();
            enemyBots.drawHealthBar(g2);
        }
    }

    public void prepareEnemyBot() {
            enemies.clear();
    }

    public void update(Game game) {
        for (Character enemyBots : getAllEnemyBots()) {
            enemyBots.moveCharacter(game);
            enemyBots.addShooting("bullet", game.getBullets(), 0.005f, 0.005f, 0.007f, 0.0f, false);
            enemyBots.changeCharacterImg();
            enemyBots.changeCharacterDirection();
            enemyBots.addJumping(game);
        }
        checkBulletDamage(game.getBullets());
    }

    public void checkBulletDamage(ArrayList<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            for (Character enemyBots : getAllEnemyBots()) {
                if (bullet.getRectangle().intersects(enemyBots.getRectangle()) && bullet.isFriendlyBullet()) {
                    enemyBots.loseHealth();
                }
            }
        }
        for (Character enemyBot : getAllEnemyBots()) {
            bullets.removeIf(bullet -> bullet.getRectangle().intersects(enemyBot.getRectangle()) &&
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

    public void loadEnemyBots(LevelStateManager levelState) {
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
                enemies.add(new Character(getStandingImage(levelState), null, getRightAnimation(levelState), getLeftAnimation(levelState),
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

    private BufferedImage getStandingImage(LevelStateManager levelState) {
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

    private Animation getRightAnimation(LevelStateManager levelState) {
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

    private Animation getLeftAnimation(LevelStateManager levelState) {
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

    public void addLevel1Enemies(LevelStateManager levelState) {
        /*enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[0], enemyLeftAnimation[0],
                0.8f, 0.2f, 0.03f, 0.12f, 0.002f, 0.1f, 2));
        enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[0], enemyLeftAnimation[0],
                1.0f, 0.2f, 0.03f, 0.12f, -0.004f, 0.1f, 5));
        enemies.add(new Character(getStandingImage(), null, enemyRightAnimation[0], enemyLeftAnimation[0],
                0.7f, 0.2f, 0.03f, 0.12f, 0.003f, 0.1f, 2));*/
    }

    public void addLevel2Enemies(LevelStateManager levelState) {
        enemies.add(new Character(getStandingImage(levelState), null, enemyRightAnimation[1], enemyLeftAnimation[1],
                0.75f, 0.2f, 0.03f, 0.12f, 0.003f, 0.35f, 2, 0));
        enemies.add(new Character(getStandingImage(levelState), null, enemyRightAnimation[1], enemyLeftAnimation[1],
                0.80f, 0.2f, 0.03f, 0.12f, -0.003f, 0.32f, 10, 0));
    }

    public void addEnemies(LevelStateManager levelState) {
        if (levelState.getState() == LevelState.LEVEL_1) {
            addLevel1Enemies(levelState);
        }
        if (levelState.getState() == LevelState.LEVEL_2) {
            addLevel2Enemies(levelState);
        }
    }
}
