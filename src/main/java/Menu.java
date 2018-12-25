import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements ActionListener, MouseListener, KeyListener {
    private GameStateManager gameState;
    private LevelStateManager levelState;
    private boolean showMainButtons = true;
    private boolean showStages = false;

    private RectangleImage continueButton;
    private RectangleImage exit;
    private RectangleImage selectStage;
    private RectangleImage stage1;
    private RectangleImage stage2;

    private RectangleImage menuBackground;

    private Platform platform;
    private EnemyBot enemyBot;
    private Background background;
    private Save save;
    private Dialogue dialogue;
    private AutoSave autoSave;

    public Menu() {
        gameState = new GameStateManager();
        levelState = new LevelStateManager();
        platform = new Platform();
        enemyBot = new EnemyBot();
        background = new Background();
        save = new Save();
        dialogue = new Dialogue();
        autoSave = new AutoSave();

        menuBackground = new RectangleImage(GameImage.getImage("background"), 0.0f, 0.0f, 1.0f, 1.0f);

        continueButton = new RectangleImage(GameImage.getImage("menu/continue"), 0.45f, 0.2f, 0.1f, 0.1f);
        selectStage = new RectangleImage(GameImage.getImage("menu/select_stage"), 0.45f, 0.4f, 0.1f, 0.1f);
        exit = new RectangleImage(GameImage.getImage("menu/exit"), 0.45f, 0.6f, 0.1f, 0.1f);
        stage1 = new RectangleImage(GameImage.getImage("menu/stage_1"), 0.45f, 0.1f, 0.1f, 0.1f);
        stage2 = new RectangleImage(GameImage.getImage("menu/stage_1"), 0.45f, 0.3f, 0.1f, 0.1f);
    }

    public void renderMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        menuBackground.draw(g2, this);
        if (showMainButtons) {
            continueButton.draw(g2, this);
            exit.draw(g2, this);
            selectStage.draw(g2, this);
        }
        if (showStages) {
            stage1.draw(g2, this);
            stage2.draw(g2, this);
        }
    }

    private boolean isOnButton(RectangleImage rectangleImage, MouseEvent e) {
        float mx = e.getX();
        float my = e.getY();
        return mx >= rectangleImage.getRectangle().x && mx <= rectangleImage.getRectangle().x + rectangleImage.getRectangle().width &&
                my >= rectangleImage.getRectangle().y && my <= rectangleImage.getRectangle().y + rectangleImage.getRectangle().height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (showMainButtons) {
            //continue
            if (isOnButton(continueButton, e)) {
                gameState.setState(GameState.IN_GAME);
                levelState.setState(LevelState.LEVEL_1);
                levelState.loadLevelState();
                save.loadFile("save player.txt");
                Player.loadPlayer = true;
                enemyBot.loadEnemyBots();
                platform.loadPlatforms();
                background.loadBackground();
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
                gameState.setState(GameState.IN_GAME);
                levelState.setState(LevelState.LEVEL_1);
                enemyBot.addLevel1Enemies();
                dialogue.addLevel1Dialogues();
                autoSave.addLevel1Checkpoints();
            }
            //stage 2
            if (isOnButton(stage2, e)) {
                gameState.setState(GameState.IN_GAME);
                levelState.setState(LevelState.LEVEL_2);
                enemyBot.addLevel2Enemies();
            }
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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !showMainButtons) {
            showMainButtons = true;
            showStages = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
