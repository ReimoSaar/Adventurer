import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JPanel implements KeyListener, ActionListener, MouseListener {
    private Image dblImage;
    private Graphics dblGraphics;
    @Getter
    private final Player player;

    @Getter
    private final LevelStateManager levelState;

    @Getter
    private final Platform platform;
    private final PauseMenu pauseMenu;
    @Getter
    private final EnemyBot enemyBot;
    private final Timer timer = new Timer(16, this);
    @Getter
    private final GameStateManager gameState;
    private final Menu menu;
    @Getter
    private final Background gameBackground;
    private final FPS fps;
    @Getter
    private ArrayList<Bullet> bullets;
    private final Save save;
    private final DeathMenu deathMenu;
    private final MenuMusic menuMusic;
    @Getter
    private final Dialogue dialogue;
    @Getter
    private final AutoSave autoSave;

    public Game() {
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        levelState = new LevelStateManager();
        player = new Player();
        platform = new Platform();
        pauseMenu = new PauseMenu();
        menu = new Menu();
        gameState = new GameStateManager();
        enemyBot = new EnemyBot();
        gameBackground = new Background();
        fps = new FPS();
        bullets = new ArrayList<>();
        save = new Save();
        deathMenu = new DeathMenu();
        menuMusic = new MenuMusic();
        dialogue = new Dialogue();
        autoSave = new AutoSave();
        timer.start();
    }

    public void paint(Graphics g) {
        dblImage = createImage(getWidth(), getHeight());
        dblGraphics = dblImage.getGraphics();
        paintComponent(dblGraphics);
        g.drawImage(dblImage, 0, 0, this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (gameState.getState() == GameState.IN_GAME) {
            gameBackground.renderBackground(g, this, levelState);
            platform.renderPlatforms(g, this, levelState);
            bullets.forEach(bullet -> bullet.drawBullet(g2, this));
            player.renderPlayer(g, this);
            enemyBot.renderEnemyBot(g, this);
            autoSave.render(g);
        }
        if (gameState.getState() == GameState.MENU) {
            menu.renderMenu(g, this);
        }
        if (gameState.getState() == GameState.PAUSE_MENU) {
            gameBackground.renderBackground(g, this, levelState);
            platform.renderPlatforms(g, this, levelState);
            bullets.forEach(bullet -> bullet.drawBullet(g2, this));
            player.renderPlayer(g, this);
            pauseMenu.renderPauseMenu(g, this);
            autoSave.render(g);
        }
        if (gameState.getState() == GameState.DEATH_MENU) {
            gameBackground.renderBackground(g, this, levelState);
            platform.renderPlatforms(g, this, levelState);
            enemyBot.renderEnemyBot(g, this);
            deathMenu.renderDeathMenu(g, this);
            autoSave.render(g);
        }
        if (gameState.getState() == GameState.IN_DIALOGUE) {
            gameBackground.renderBackground(g, this, levelState);
            platform.renderPlatforms(g, this, levelState);
            bullets.forEach(bullet -> bullet.drawBullet(g2, this));
            player.renderPlayer(g, this);
            enemyBot.renderEnemyBot(g, this);
            dialogue.renderDialogue(g, this);
            autoSave.render(g);
        }
        fps.renderFPS(g);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        save.checkFileExistance();
        menuMusic.playMenuMusic(gameState);
        if (gameState.getState() == GameState.MENU) {
            player.preparePlayerObjects();
            platform.relocatePlatforms();
            enemyBot.prepareEnemyBot();
            gameBackground.prepareBackground();
            bullets.clear();
            dialogue.prepareDialogue();
            autoSave.prepareCheckpoints();
        }
        if (Player.loadPlayer) {
            player.loadPlayer();
        }
        if (gameState.getState() == GameState.IN_GAME) {
            player.update(this);
            enemyBot.update(this);
            platform.update(levelState);
            gameBackground.update(this);
            bullets.forEach(bullet -> bullet.update(this));
            bullets.removeIf(bullet -> bullet.isCollided(this));
            dialogue.update(this);
            autoSave.update(this);
        }
        fps.actionPerformed(e);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (gameState.getState() == GameState.IN_GAME) {
            player.keyPressed(e, this);
        }
        if (gameState.getState() == GameState.IN_GAME || gameState.getState() == GameState.PAUSE_MENU) {
            pauseMenu.keyPressed(e, gameState);
        }
        if (gameState.getState() == GameState.MENU) {
            menu.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameState.getState() == GameState.MENU) {
            menu.mousePressed(e, this);
        }
        if (gameState.getState() == GameState.PAUSE_MENU) {
            pauseMenu.mousePressed(e, this);
        }
        if (gameState.getState() == GameState.DEATH_MENU) {
            deathMenu.mousePressed(e, this);
        }
        if (gameState.getState() == GameState.IN_DIALOGUE) {
            dialogue.mousePressed(gameState);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
