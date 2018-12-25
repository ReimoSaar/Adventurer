import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeathMenu extends JPanel implements MouseListener, ActionListener {
    private RectangleImage restart;
    private RectangleImage pauseBackground;

    private GameStateManager gameState;
    private LevelStateManager levelState;
    private EnemyBot enemyBot;
    private Background background;
    private Bullet bullet;
    private Player player;
    private Platform platform;

    public DeathMenu() {
        restart = new RectangleImage(GameImage.getImage("restart"), 0.45f, 0.4f, 0.1f, 0.1f);
        pauseBackground = new RectangleImage(GameImage.getImage("pausemenu_background"), 0.0f, 0.0f, 1.0f, 1.0f);

        gameState = new GameStateManager();
        levelState = new LevelStateManager();
        enemyBot = new EnemyBot();
        background = new Background();
        bullet = new Bullet();
        player = new Player();
        platform = new Platform();
    }

    public void renderDeathMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        pauseBackground.draw(g2, this);
        restart.draw(g2, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        float mx = e.getX();
        float my = e.getY();
        if (mx >= restart.getRectangle().x && mx <= restart.getRectangle().x + restart.getRectangle().width &&
            my >= restart.getRectangle().y && my <= restart.getRectangle().y + restart.getRectangle().height) {
            gameState.setState(GameState.IN_GAME);
            bullet.removeAllBullets();
            player.preparePlayerObjects();
            enemyBot.prepareEnemyBot();
            background.prepareBackground();
            enemyBot.addEnemies();
            platform.relocatePlatforms();
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
