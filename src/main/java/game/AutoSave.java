package game;

import game.characters.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoSave {
    private Save saveGame;
    private static List<Checkpoint> autoSaveLocations = Collections.synchronizedList(new ArrayList<>());

    public AutoSave() {
        saveGame = new Save();
    }

    public void update(Game game) {
        addInteraction(game);
    }

    public void addLevel1Checkpoints() {
        autoSaveLocations.add(new Checkpoint(0.9f));
        autoSaveLocations.add(new Checkpoint(1.7f));
    }

    public void addInteraction(Game game) {
        for (Checkpoint checkpoint : autoSaveLocations) {
            checkpoint.rectangle.x += game.getPlatform().getxVel();
            if (game.getPlayer().getRectangle().x >= checkpoint.rectangle.x) {
                saveGame.saveFile("save player.txt");
                game.getEnemyBot().saveEnemyBots();
                game.getPlatform().savePlatforms(game.getLevelState());
                game.getGameBackground().saveBackground(game.getLevelState());
                game.getLevelState().saveLevelState();
            }
        }

        autoSaveLocations.removeIf(checkpoint -> game.getPlayer().getRectangle().x >= checkpoint.rectangle.x);
    }

    public void render(Graphics g) {
        for (Checkpoint checkpoint : autoSaveLocations) {
            checkpoint.renderCheckpoint(g);
        }
    }

    public void prepareCheckpoints() {
        if (autoSaveLocations.size() > 0) {
            autoSaveLocations.clear();
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
