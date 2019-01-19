import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PauseMenu {

    private Save saveGame;

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

        saveGame = new Save();
    }

    private boolean isOnButton(RectangleImage rectangleImage, MouseEvent e) {
        float mx = e.getX();
        float my = e.getY();
        return mx >= rectangleImage.getRectangle().x && mx <= rectangleImage.getRectangle().x + rectangleImage.getRectangle().width &&
                my >= rectangleImage.getRectangle().y && my <= rectangleImage.getRectangle().y + rectangleImage.getRectangle().height;
    }

    public void renderPauseMenu(Graphics g, ActionListener imageObserver) {
        Graphics2D g2 = (Graphics2D) g;
        pauseBackground.draw(g2, imageObserver);
        resume.draw(g2, imageObserver);
        load.draw(g2, imageObserver);
        save.draw(g2, imageObserver);
        mainMenu.draw(g2, imageObserver);
        exit.draw(g2, imageObserver);
    }

    public void keyPressed(KeyEvent e, GameStateManager gameState) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameState.getState() == GameState.IN_GAME) {
            gameState.setState(GameState.PAUSE_MENU);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameState.getState() == GameState.PAUSE_MENU) {
            gameState.setState(GameState.IN_GAME);
        }
    }

    public void mousePressed(MouseEvent e, Game game) {
        //resume
        if (isOnButton(resume, e)) {
                game.getGameState().setState(GameState.IN_GAME);
        }
        //load
        if (isOnButton(load, e)) {
            game.getLevelState().loadLevelState();
            saveGame.loadFile("save player.txt");
            Player.loadPlayer = true;
            game.getEnemyBot().loadEnemyBots(game.getLevelState());
            game.getPlatform().loadPlatforms(game.getLevelState());
            game.getGameBackground().loadBackground(game.getLevelState());
        }
        //save
        if (isOnButton(save, e)) {
            saveGame.saveFile("save player.txt");
            game.getEnemyBot().saveEnemyBots();
            game.getPlatform().savePlatforms(game.getLevelState());
            game.getGameBackground().saveBackground(game.getLevelState());
            game.getLevelState().saveLevelState();
        }
        //main menu
        if (isOnButton(mainMenu, e)) {
            game.getGameState().setState(GameState.MENU);

        }
        //exit
        if (isOnButton(exit, e)) {
            System.exit(-1);
        }
    }
}
