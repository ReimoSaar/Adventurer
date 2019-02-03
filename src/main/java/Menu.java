import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

public class Menu {
    private boolean showMainButtons = true;
    private boolean showStages = false;

    private RectangleImage continueButton;
    private RectangleImage exit;
    private RectangleImage selectStage;
    private RectangleImage stage1;
    private RectangleImage stage2;

    private RectangleImage menuBackground;

    private Save save;

    public Menu() {
        save = new Save();

        menuBackground = new RectangleImage(GameImage.getImage("background"), 0.0f, 0.0f, 1.0f, 1.0f);

        continueButton = new RectangleImage(GameImage.getImage("menu/continue"), 0.45f, 0.2f, 0.1f, 0.1f);
        selectStage = new RectangleImage(GameImage.getImage("menu/select_stage"), 0.45f, 0.4f, 0.1f, 0.1f);
        exit = new RectangleImage(GameImage.getImage("menu/exit"), 0.45f, 0.6f, 0.1f, 0.1f);
        stage1 = new RectangleImage(GameImage.getImage("menu/stage_1"), 0.45f, 0.1f, 0.1f, 0.1f);
        stage2 = new RectangleImage(GameImage.getImage("menu/stage_1"), 0.45f, 0.3f, 0.1f, 0.1f);
    }

    public void renderMenu(Graphics g, ImageObserver imageObserver) {
        Graphics2D g2 = (Graphics2D) g;
        menuBackground.draw(g2, imageObserver);
        if (showMainButtons) {
            continueButton.draw(g2, imageObserver);
            exit.draw(g2, imageObserver);
            selectStage.draw(g2, imageObserver);
        }
        if (showStages) {
            stage1.draw(g2, imageObserver);
            stage2.draw(g2, imageObserver);
        }
    }

    private boolean isOnButton(RectangleImage rectangleImage, MouseEvent e) {
        float mx = e.getX();
        float my = e.getY();
        return mx >= rectangleImage.getRectangle().x && mx <= rectangleImage.getRectangle().x + rectangleImage.getRectangle().width &&
                my >= rectangleImage.getRectangle().y && my <= rectangleImage.getRectangle().y + rectangleImage.getRectangle().height;
    }

    public void mousePressed(MouseEvent e, Game game) {
        if (showMainButtons) {
            //continue
            if (isOnButton(continueButton, e)) {
                game.getGameState().setState(GameState.IN_GAME);
                game.getLevelState().setState(LevelState.LEVEL_1);
                game.getLevelState().loadLevelState();
                save.loadFile("save player.txt");
                Player.loadPlayer = true;
                game.getEnemyBot().loadEnemyBots(game.getLevelState());
                game.getPlatform().loadPlatforms(game.getLevelState());
                game.getGameBackground().loadBackground(game.getLevelState());
            }
            //select stage
            if (isOnButton(selectStage, e)) {
                showStages = true;
                showMainButtons = false;
            }
            //exit
            if (isOnButton(exit, e)) {
                System.exit(-1);
            }
        }
        if (showStages) {
            //stage 1
            if (isOnButton(stage1, e)) {
                game.getGameState().setState(GameState.IN_GAME);
                game.getLevelState().setState(LevelState.LEVEL_1);
                game.getEnemyBot().addLevel1Enemies(game.getLevelState());
                game.getDialogue().addLevel1Dialogues();
                game.getAutoSave().addLevel1Checkpoints();
            }
            //stage 2
            if (isOnButton(stage2, e)) {
                game.getGameState().setState(GameState.IN_GAME);
                game.getLevelState().setState(LevelState.LEVEL_2);
                game.getEnemyBot().addLevel2Enemies(game.getLevelState());
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !showMainButtons) {
            showMainButtons = true;
            showStages = false;
        }
    }
}
