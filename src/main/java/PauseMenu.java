import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PauseMenu extends JPanel implements ActionListener, KeyListener, MouseListener {

    private GameStateManager gameState;
    private Save saveGame;
    private Platform platform;
    private EnemyBot enemyBot;
    private Background background;
    private LevelStateManager levelState;
    private AutoSave autoSave;

    private RectangleImage pauseBackground;
    private RectangleImage resume;
    private RectangleImage load;
    private RectangleImage save;
    private RectangleImage mainMenu;
    private RectangleImage exit;

    public PauseMenu() {
        pauseBackground = new RectangleImage(GameImage.getImage("pausemenu_background"), 0.0f, 0.0f, 1.0f, 1.0f);
        resume = new RectangleImage(GameImage.getImage("resume"), 0.45f, 0.3f, 0.1f, 0.1f);
        load = new RectangleImage(GameImage.getImage("load"), 0.45f, 0.4f, 0.1f, 0.1f);
        save = new RectangleImage(GameImage.getImage("save"), 0.45f, 0.5f, 0.1f, 0.1f);
        mainMenu = new RectangleImage(GameImage.getImage("main_menu"), 0.45f, 0.6f, 0.1f, 0.1f);
        exit = new RectangleImage(GameImage.getImage("menu/exit"), 0.45f, 0.7f, 0.1f, 0.1f);

        gameState = new GameStateManager();
        saveGame = new Save();
        platform = new Platform();
        enemyBot = new EnemyBot();
        background = new Background();
        levelState = new LevelStateManager();
        autoSave = new AutoSave();
    }

    private boolean isOnButton(RectangleImage rectangleImage, MouseEvent e) {
        float mx = e.getX();
        float my = e.getY();
        return mx >= rectangleImage.getRectangle().x && mx <= rectangleImage.getRectangle().x + rectangleImage.getRectangle().width &&
                my >= rectangleImage.getRectangle().y && my <= rectangleImage.getRectangle().y + rectangleImage.getRectangle().height;
    }

    public void renderPauseMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        pauseBackground.draw(g2, this);
        resume.draw(g2, this);
        load.draw(g2, this);
        save.draw(g2, this);
        mainMenu.draw(g2, this);
        exit.draw(g2, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameState.getState() == GameState.IN_GAME) {
            gameState.setState(GameState.PAUSE_MENU);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameState.getState() == GameState.PAUSE_MENU) {
            gameState.setState(GameState.IN_GAME);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //resume
        if (isOnButton(resume, e)) {
                gameState.setState(GameState.IN_GAME);
        }
        //load
        if (isOnButton(load, e)) {
            levelState.loadLevelState();
            saveGame.loadFile("save player.txt");
            Player.loadPlayer = true;
            enemyBot.loadEnemyBots();
            platform.loadPlatforms();
            background.loadBackground();
        }
        //save
        if (isOnButton(save, e)) {
            saveGame.saveFile("save player.txt");
            enemyBot.saveEnemyBots();
            platform.savePlatforms();
            background.saveBackground();
            levelState.saveLevelState();
        }
        //main menu
        if (isOnButton(mainMenu, e)) {
            gameState.setState(GameState.MENU);

        }
        //exit
        if (isOnButton(exit, e)) {
            System.exit(-1);
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
