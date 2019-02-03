import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

public class DeathMenu {
    private RectangleImage restart;
    private RectangleImage pauseBackground;

    public DeathMenu() {
        restart = new RectangleImage(GameImage.getImage("restart"), 0.45f, 0.4f, 0.1f, 0.1f);
        pauseBackground = new RectangleImage(GameImage.getImage("pausemenu_background"), 0.0f, 0.0f, 1.0f, 1.0f);
    }

    public void renderDeathMenu(Graphics g, ImageObserver imageObserver) {
        Graphics2D g2 = (Graphics2D) g;
        pauseBackground.draw(g2, imageObserver);
        restart.draw(g2, imageObserver);
    }

    public void mousePressed(MouseEvent e, Game game) {
        float mx = e.getX();
        float my = e.getY();
        if (mx >= restart.getRectangle().x && mx <= restart.getRectangle().x + restart.getRectangle().width &&
            my >= restart.getRectangle().y && my <= restart.getRectangle().y + restart.getRectangle().height) {
            game.getGameState().setState(GameState.IN_GAME);
            game.getBullets().removeAll(game.getBullets());
            game.getPlayer().preparePlayerObjects();
            game.getEnemyBot().prepareEnemyBot();
            game.getGameBackground().prepareBackground();
            game.getEnemyBot().addEnemies(game.getLevelState());
            game.getPlatform().relocatePlatforms();
        }
    }
}
