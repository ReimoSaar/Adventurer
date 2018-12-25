import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.BatchUpdateException;

public class Game extends JPanel implements KeyListener, ActionListener, MouseListener {
    private Image dblImage;
    private Graphics dblGraphics;
    private final Player player;
    private final Platform platform;
    private final PauseMenu pauseMenu;
    private final EnemyBot enemyBot;
    private final Timer timer = new Timer(16, this);
    private final GameStateManager gameState;
    private final Menu menu;
    private final Background background;
    private final FPS fps;
    private final Bullet bullet;
    private final Save save;
    private final DeathMenu deathMenu;
    private final MenuMusic menuMusic;
    private final Dialogue dialogue;
    private final AutoSave autoSave;

    public Game() {
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        player = new Player();
        platform = new Platform();
        pauseMenu = new PauseMenu();
        menu = new Menu();
        gameState = new GameStateManager();
        enemyBot = new EnemyBot();
        background = new Background();
        fps = new FPS();
        bullet = new Bullet();
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
        if (gameState.getState() == GameState.IN_GAME) {
            background.renderBackground(g);
            platform.renderPlatforms(g);
            player.renderPlayer(g);
            enemyBot.renderEnemyBot(g);
            autoSave.render(g);
        }
        if (gameState.getState() == GameState.MENU) {
            menu.renderMenu(g);
        }
        if (gameState.getState() == GameState.PAUSE_MENU) {
            background.renderBackground(g);
            platform.renderPlatforms(g);
            player.renderPlayer(g);
            pauseMenu.renderPauseMenu(g);
            autoSave.render(g);
        }
        if (gameState.getState() == GameState.DEATH_MENU) {
            background.renderBackground(g);
            platform.renderPlatforms(g);
            enemyBot.renderEnemyBot(g);
            deathMenu.renderDeathMenu(g);
            autoSave.render(g);
        }
        if (gameState.getState() == GameState.IN_DIALOGUE) {
            background.renderBackground(g);
            platform.renderPlatforms(g);
            player.renderPlayer(g);
            enemyBot.renderEnemyBot(g);
            dialogue.renderDialogue(g);
            autoSave.render(g);
        }
        fps.renderFPS(g);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        save.checkFileExistance();
        menuMusic.playMenuMusic();
        if (gameState.getState() == GameState.MENU) {
            player.preparePlayerObjects();
            platform.relocatePlatforms();
            enemyBot.prepareEnemyBot();
            background.prepareBackground();
            dialogue.prepareDialogue();
            autoSave.prepareCheckpoints();
        }
        if (Player.loadPlayer) {
            player.loadPlayer();
        }
        if (gameState.getState() == GameState.IN_GAME) {
            player.actionPerformed(e);
            enemyBot.actionPerformed(e);
            platform.actionPerformed(e);
            background.actionPerformed(e);
            bullet.actionPerformed(e);
            dialogue.actionPerformed(e);
            autoSave.actionPerformed(e);
        }
        fps.actionPerformed(e);
    }

    public void keyTyped(KeyEvent e) {
        player.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        if (gameState.getState() == GameState.IN_GAME) {
            player.keyTyped(e);
            player.keyPressed(e);
        }
        if (gameState.getState() == GameState.IN_GAME || gameState.getState() == GameState.PAUSE_MENU) {
            pauseMenu.keyPressed(e);
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
            menu.mousePressed(e);
        }
        if (gameState.getState() == GameState.PAUSE_MENU) {
            pauseMenu.mousePressed(e);
        }
        if (gameState.getState() == GameState.DEATH_MENU) {
            deathMenu.mousePressed(e);
        }
        if (gameState.getState() == GameState.IN_DIALOGUE) {
            dialogue.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dialogue.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
