import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AutoSave extends JPanel implements ActionListener {
    private GameStateManager gameState;
    private Save saveGame;
    private Platform platform;
    private EnemyBot enemyBot;
    private Background background;
    private LevelStateManager levelState;
    private static ArrayList<Checkpoint> autoSaveLocations;

    public AutoSave() {
        saveGame = new Save();
        platform = new Platform();
        enemyBot = new EnemyBot();
        background = new Background();
        autoSaveLocations = new ArrayList<>();
        levelState = new LevelStateManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addInteraction();
    }

    public void addLevel1Checkpoints() {
        autoSaveLocations.add(new Checkpoint(0.9f));
        autoSaveLocations.add(new Checkpoint(1.7f));
    }

    public void addInteraction() {
        for (Checkpoint checkpoint : autoSaveLocations) {
            checkpoint.rectangle.x += platform.getxVel();
            if (Player.getPlayer().getRectangle().x >= checkpoint.rectangle.x) {
                saveGame.saveFile("save player.txt");
                enemyBot.saveEnemyBots();
                platform.savePlatforms();
                background.saveBackground();
                levelState.saveLevelState();
            }
        }

        autoSaveLocations.removeIf(checkpoint -> Player.getPlayer().getRectangle().x >= checkpoint.rectangle.x);
    }

    public void render(Graphics g) {
        for (Checkpoint checkpoint : autoSaveLocations) {
            checkpoint.renderCheckpoint(g);
        }
    }

    public void prepareCheckpoints() {
        if (autoSaveLocations.size() > 0) {
            autoSaveLocations.removeAll(autoSaveLocations);
        }
    }

    public void addCheckpointByLevel() {
        if (levelState.getState() == LevelState.LEVEL_1) {
            addLevel1Checkpoints();
        }
    }

    private class Checkpoint {
        private Rectangle rectangle;
        private int screenHeight;
        private float screenWidth;

        private Checkpoint(float x) {
            Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
            x = x * resolution.width;
            this.rectangle = new Rectangle((int) x, 0, 0, 0);
            screenHeight = resolution.height;
            screenWidth = resolution.width;
        }

        private void renderCheckpoint(Graphics g) {
            new Text(g, "CHECKPOINT", rectangle.x / screenWidth, 0.1f, 60, "arial", Color.YELLOW, 1);
        }
    }
}
